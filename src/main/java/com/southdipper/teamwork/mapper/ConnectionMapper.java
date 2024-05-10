package com.southdipper.teamwork.mapper;

import com.southdipper.teamwork.pojo.Connection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ConnectionMapper {
    Connection getConnection(@Param("user1Id")Integer user1Id, @Param("user2Id")Integer user2Id);
    void createConnection(Connection connection);
    void updateConnection(Connection connection);
    void setUserOnline(Connection connection);
    List<Connection> getConnectionInfo1(Integer userId);
    List<Connection> getConnectionInfo2(Integer userId);
}
