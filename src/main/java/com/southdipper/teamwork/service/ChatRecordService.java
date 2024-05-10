package com.southdipper.teamwork.service;

import com.southdipper.teamwork.pojo.ChatRecord;

import java.util.List;

public interface ChatRecordService {
    void insertChatRecord(ChatRecord chatRecord);
    List<ChatRecord> getRecord(Integer connectionId);
}
