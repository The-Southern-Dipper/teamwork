package com.southdipper.teamwork.endpoint;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.southdipper.teamwork.endpoint.pojo.Message;
import com.southdipper.teamwork.endpoint.pojo.PutMessage;
import com.southdipper.teamwork.pojo.ChatRecord;
import com.southdipper.teamwork.pojo.Connection;
import com.southdipper.teamwork.pojo.Result;
import com.southdipper.teamwork.service.ChatRecordService;
import com.southdipper.teamwork.service.ConnectionService;
import com.southdipper.teamwork.service.JwtVerifyService;
import com.southdipper.teamwork.util.JwtUtil;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@ServerEndpoint("/chat/send/{senderId}")
public class SendEndpoint {
    private static JwtVerifyService jwtVerifyService;
    private static ConnectionService connectionService;
    private static ChatRecordService chatRecordService;
    private static Map<Integer, Session> map = new HashMap<>();
    Session session;
    Integer reciever = null;

    @OnMessage
    public void onMessage(String msg) throws IOException {
        Message message = JSON.parseObject(msg, Message.class);
        String token = message.getAuthorization();
        // 不管什么请求都是先验证
        try {
            jwtVerifyService.verify(token);
        }
        catch (Exception e) {
            Result ret = Result.error(e.toString());
            session.getAsyncRemote().sendText(JSON.toJSONString(ret, SerializerFeature.WriteMapNullValue));
            this.session.close(new CloseReason(CloseReason.CloseCodes.CANNOT_ACCEPT, "登录凭证过期或错误"));
        }
        // 如果是单纯的验证请求，那直接不用运行后续了
        if(message.getMessageType() == 0) {
            Result ret = Result.success("验证成功");
            session.getAsyncRemote().sendText(JSON.toJSONString(ret, SerializerFeature.WriteMapNullValue));
            return;
        }
        // 先拿到发送方ID和接受方ID, 看看有无连接存在
        Integer sender = JwtUtil.getIdFromToken(token);
        reciever = message.getRecieverId();
        Connection connection = connectionService.getConnection(sender, message.getRecieverId());
        ChatRecord chatRecord = new ChatRecord();
        if(connection == null) { // 没有连接就需要创建连接
             // 在线的定义：用户建立WebSocket连接进行聊天
             Connection newConnection = new Connection();
             newConnection.setUser1Id(sender);
             newConnection.setUser2Id(message.getRecieverId());
             // 由于是sender发送的，所以sender一定在线
             newConnection.setUser1Online(true);
             // 由于之前不存在连接，所以reciever根本没有和sender聊过天，一定是不在线的
             newConnection.setUser2Online(false);
             newConnection.setUser1Unread(0);
             newConnection.setUser2Unread(0);
             newConnection.setLatestContentType(message.getMessageType());
             newConnection.setLatestContent(message.getContent());
             connectionService.createConnection(newConnection);
        }
        connection = connectionService.getConnection(sender, message.getRecieverId());
        boolean isOne = sender.equals(connection.getUser1Id());
        boolean recieverOnline = isOne? connection.isUser2Online() : connection.isUser1Online();
        if(!recieverOnline) {
            if(isOne) {
                connection.setUser2Unread(connection.getUser2Unread() + 1);
            }
            else {
                connection.setUser1Unread(connection.getUser1Unread() + 1);
            }
            connection.setLatestContentType(message.getMessageType());
            connection.setLatestContent(message.getContent());
            connectionService.updateConnection(connection);
        }
        else {
            Session recieverSession = map.get(message.getRecieverId());
            PutMessage putMessage =
                    new PutMessage(
                            1,
                            sender,
                            LocalDateTime.now(),
                            message.getContentType(),
                            message.getContent());
            recieverSession.getAsyncRemote().sendText(JSON.toJSONString(putMessage, SerializerFeature.WriteMapNullValue));
        }
        chatRecord.setConnectionId(connection.getId());
        chatRecord.setSenderId(sender);
        chatRecord.setRecieverId(message.getRecieverId());
        chatRecord.setContentType(message.getContentType());
        chatRecord.setContent(message.getContent());
        chatRecordService.insertChatRecord(chatRecord);
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("senderId")Integer senderId) {
        map.put(senderId, session);
        this.session = session;
    }

    @OnClose
    public void onClose(@PathParam("senderId")Integer senderId, Session session, CloseReason reason) {
        map.remove(senderId);
        if(reciever == null) {
            return;
        }
        // 用户关闭连接，下线
        Connection connection = connectionService.getConnection(senderId, reciever);
        if(connection== null) {
            return;
        }
        if(senderId.equals(connection.getUser1Id())) {
            connection.setUser1Online(false);
        }
        else {
            connection.setUser2Online(false);
        }
        connectionService.setUserOnline(connection);
    }

    @Autowired
    public void setJwtVerifyService(JwtVerifyService jwtVerifyService) {
        SendEndpoint.jwtVerifyService = jwtVerifyService;
    }
    @Autowired
    public void setConnectionService(ConnectionService connectionService) {
        SendEndpoint.connectionService = connectionService;
    }
    @Autowired
    public void setChatRecordService(ChatRecordService chatRecordService) {
        SendEndpoint.chatRecordService = chatRecordService;
    }
}
