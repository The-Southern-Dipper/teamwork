package com.southdipper.teamwork.controller;

import com.southdipper.teamwork.pojo.ChatRecord;
import com.southdipper.teamwork.pojo.Connection;
import com.southdipper.teamwork.pojo.Result;
import com.southdipper.teamwork.service.ChatRecordService;
import com.southdipper.teamwork.service.ConnectionService;
import com.southdipper.teamwork.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    ConnectionService connectionService;
    @Autowired
    ChatRecordService chatRecordService;
    @GetMapping("/getRecord")
    public Result getRecord(Integer id) {
        Integer userId = ThreadLocalUtil.getId();
        // 获取连接
        Connection connection = connectionService.getConnection(userId, id);
        // 先获取出来记录
        List<ChatRecord> records = chatRecordService.getRecord(connection.getId());
        // 上线了
        if(userId.equals(connection.getUser1Id())) {
            connection.setUser1Online(true);
        }
        else {
            connection.setUser2Online(true);
        }
        connectionService.setUserOnline(connection);
        return Result.success(records);
    }
}
