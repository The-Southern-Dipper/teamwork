package com.southdipper.teamwork.service;


import com.southdipper.teamwork.pojo.Connection;

public interface ConnectionService {
    Connection getConnection(Integer user1Id, Integer user2Id);
    void createConnection(Connection connection);
    void updateConnection(Connection connection);
    void setUserOnline(Connection connection);
}
