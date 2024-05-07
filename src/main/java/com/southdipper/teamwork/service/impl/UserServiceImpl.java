package com.southdipper.teamwork.service.impl;

import com.southdipper.teamwork.mapper.UserMapper;
import com.southdipper.teamwork.pojo.User;
import com.southdipper.teamwork.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User getByUserName(String username) {
        return userMapper.getByUserName(username);
    }

    @Override
    public void register(String username, String password,String email) {
        userMapper.register(username, password,email);
    }

    @Override
    public void updateMsg(User user) {
        userMapper.updateMsg(user);
    }

    @Override
    public void changePwd(String username, String newPwd) {
        userMapper.changePwd(username, newPwd);
    }
}
