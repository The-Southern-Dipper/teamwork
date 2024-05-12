package com.southdipper.teamwork.service;

import com.southdipper.teamwork.pojo.ChatRecord;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ChatRecordService {
    @Transactional
    void insertChatRecord(ChatRecord chatRecord);
    @Transactional
    List<ChatRecord> getRecord(Integer connectionId);
}
