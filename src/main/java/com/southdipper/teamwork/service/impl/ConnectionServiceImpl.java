package com.southdipper.teamwork.service.impl;

import com.southdipper.teamwork.mapper.ConnectionMapper;
import com.southdipper.teamwork.pojo.Connection;
import com.southdipper.teamwork.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConnectionServiceImpl implements ConnectionService {
    @Autowired
    ConnectionMapper connectionMapper;
    @Override
    public Connection getConnection(Integer user1Id, Integer user2Id) {
        return connectionMapper.getConnection(user1Id, user2Id);
    }
    @Override
    public void createConnection(Connection connection) {
        connectionMapper.createConnection(connection);
    }

    @Override
    public void updateConnection(Connection connection) {
        connectionMapper.updateConnection(connection);
    }

    @Override
    public void setUserOnline(Connection connection) {
        connectionMapper.setUserOnline(connection);
    }
}
