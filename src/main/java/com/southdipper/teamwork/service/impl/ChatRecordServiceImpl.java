package com.southdipper.teamwork.service.impl;

import com.southdipper.teamwork.mapper.ChatRecordMapper;
import com.southdipper.teamwork.pojo.ChatRecord;
import com.southdipper.teamwork.service.ChatRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatRecordServiceImpl implements ChatRecordService {
    @Autowired
    ChatRecordMapper chatRecordMapper;
    @Override
    public void insertChatRecord(ChatRecord chatRecord) {
        chatRecordMapper.insertChatRecord(chatRecord);
    }

    @Override
    public List<ChatRecord> getRecord(Integer connectionId) {
        return chatRecordMapper.getRecord(connectionId);
    }
}
