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
import java.util.List;
import java.util.Map;

@Component
@ServerEndpoint("/chat/{senderId}")
public class SendEndpoint {
    private static JwtVerifyService jwtVerifyService;
    private static ConnectionService connectionService;
    private static ChatRecordService chatRecordService;
    private static final Map<Integer, Session> map = new HashMap<>();
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
        // 上述代码已经对消息进行了验证
        // 先拿到发送方ID和接受方ID, 看看有无连接存在
        Integer sender = JwtUtil.getIdFromToken(token);
        reciever = message.getRecieverId();
        Connection connection = connectionService.getConnection(sender, reciever);
        ChatRecord chatRecord = new ChatRecord();
        // 不管是获取聊天记录还是发送消息，前提都是需要有一个连接
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
        connection = connectionService.getConnection(sender, reciever);
        boolean isOne = sender.equals(connection.getUser1Id());
        if(message.getMessageType() == 1){
            // messageType为1，发送消息
            boolean recieverOnline = isOne? connection.isUser2Online() : connection.isUser1Online();
            if(!recieverOnline) { // 接收方不在线
                if(isOne) {
                    connection.setUser2Unread(connection.getUser2Unread() + 1);
                }
                else {
                    connection.setUser1Unread(connection.getUser1Unread() + 1);
                }
                connection.setLatestContentType(message.getMessageType());
                connection.setLatestContent(message.getContent());
                connectionService.updateConnection(connection);
                // 存储聊天记录到数据库
                chatRecord.setConnectionId(connection.getId());
                chatRecord.setSenderId(sender);
                chatRecord.setRecieverId(message.getRecieverId());
                chatRecord.setContentType(message.getContentType());
                chatRecord.setContent(message.getContent());
                chatRecordService.insertChatRecord(chatRecord);
            }
            else { // 接收方在线
                // 推送消息给接收方
                // 存储聊天记录到数据库
                chatRecord.setConnectionId(connection.getId());
                chatRecord.setSenderId(sender);
                chatRecord.setRecieverId(message.getRecieverId());
                chatRecord.setContentType(message.getContentType());
                chatRecord.setContent(message.getContent());
                chatRecordService.insertChatRecord(chatRecord);
                Session recieverSession = map.get(message.getRecieverId());
                List<ChatRecord> records = chatRecordService.getRecord(connection.getId());
                Result ret = Result.success(records);
                recieverSession.getBasicRemote().sendText(JSON.toJSONString(ret, SerializerFeature.WriteMapNullValue));
            }
        }
        else {
            if(isOne) {
                connection.setUser1Online(true);
            }
            else {
                connection.setUser2Online(true);
            }
            connectionService.setUserOnline(connection);
        }
        // 不管是发送消息还是接收聊天记录，都会收到最新的聊天记录列表
        List<ChatRecord> records = chatRecordService.getRecord(connection.getId());
        Result ret = Result.success(records);
        this.session.getAsyncRemote().sendText(JSON.toJSONString(ret, SerializerFeature.WriteMapNullValue));
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
        Connection connection =  connectionService.getConnection(senderId, reciever);
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
    @OnError
    public void OnError(Session session, Throwable throwable) {
        throwable.printStackTrace();
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
