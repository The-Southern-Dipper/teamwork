package com.southdipper.teamwork.mapper;

import com.southdipper.teamwork.pojo.ChatRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChatRecordMapper {
    void insertChatRecord(ChatRecord chatRecord);
    List<ChatRecord> getRecord(Integer connectionId);
}
