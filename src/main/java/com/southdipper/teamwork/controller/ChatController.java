package com.southdipper.teamwork.controller;

import com.southdipper.teamwork.pojo.ChatRecord;
import com.southdipper.teamwork.pojo.Connection;
import com.southdipper.teamwork.pojo.ConnectionResponse;
import com.southdipper.teamwork.pojo.Result;
import com.southdipper.teamwork.service.ChatRecordService;
import com.southdipper.teamwork.service.ConnectionService;
import com.southdipper.teamwork.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "*")
public class ChatController {
    @Autowired
    ConnectionService connectionService;
    @Autowired
    ChatRecordService chatRecordService;
    @GetMapping("/getConnectionInfo")
    public Result getConnectionInfo() {
        Integer userId = ThreadLocalUtil.getId();
        List<ConnectionResponse> connections1 = connectionService.getConnectionInfo1(userId);
        List<ConnectionResponse> connections2 = connectionService.getConnectionInfo2(userId);
        connections1.addAll(connections2);
        return Result.success(connections1);
    }

}
