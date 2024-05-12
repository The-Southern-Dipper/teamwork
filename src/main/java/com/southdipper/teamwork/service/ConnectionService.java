package com.southdipper.teamwork.service;


import com.southdipper.teamwork.pojo.Connection;
import com.southdipper.teamwork.pojo.ConnectionResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ConnectionService {
    CompletableFuture<Connection> getConnection(Integer user1Id, Integer user2Id);
    void createConnection(Connection connection);
    void updateConnection(Connection connection);
    void setUserOnline(Connection connection);
    List<ConnectionResponse> getConnectionInfo1(Integer userId);
    List<ConnectionResponse> getConnectionInfo2(Integer userId);
}
