package com.southdipper.teamwork.service;


import com.southdipper.teamwork.pojo.Connection;
import com.southdipper.teamwork.pojo.ConnectionResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ConnectionService {
    @Transactional
    Connection getConnection(Integer user1Id, Integer user2Id);
    @Transactional
    void createConnection(Connection connection);
    @Transactional
    void updateConnection(Connection connection);
    @Transactional
    void setUserOnline(Connection connection);
    @Transactional
    List<ConnectionResponse> getConnectionInfo1(Integer userId);
    @Transactional
    List<ConnectionResponse> getConnectionInfo2(Integer userId);
}
