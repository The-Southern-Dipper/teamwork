package com.southdipper.teamwork.service;

import com.southdipper.teamwork.pojo.User;

public interface UserService {
    User getByUserName(String username);
    User getByNickName(String nickname);
    User getByEmail(String email);
    void register(String username, String password,String email);
    void updateMsg(User user);
    void changePwd(String username, String newPwd);

}
