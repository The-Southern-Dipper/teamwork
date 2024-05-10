package com.southdipper.teamwork.service;


import com.southdipper.teamwork.pojo.Connection;

import java.util.List;

public interface ConnectionService {
    Connection getConnection(Integer user1Id, Integer user2Id);
    void createConnection(Connection connection);
    void updateConnection(Connection connection);
    void setUserOnline(Connection connection);
    List<Connection> getConnectionInfo1(Integer userId);
    List<Connection> getConnectionInfo2(Integer userId);
}
