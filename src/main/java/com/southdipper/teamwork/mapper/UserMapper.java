package com.southdipper.teamwork.mapper;

import com.southdipper.teamwork.pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Value;

@Mapper
public interface UserMapper {

    @Select("select * from user where username=#{username}")
    User getByUserName(String username);

    @Select("select * from user where nickname=#{nickname}")
    User getByNickName(String nickname);

    @Select("select * from user where email=#{email}")
    User getByEmail(String email);

    @Insert("insert into user(username,password,email,user_img,create_time,update_time) " +
            "values(#{username},#{password},#{email},#{userImg},now(),now())")
    void register(@Param("username")String username, @Param("password")String password,@Param("email")String email,@Param("userImg")String userImg);

    @Update("update user set nickname=#{nickname},email=#{email},update_time=now() where id=#{id}")
    void updateMsg(User user);

    @Update("update user set user_img=#{userImg},update_time=now() where id=#{id}")
    void updateImg(User user);

    @Update("update user set password=#{newPwd},update_time=now() where username=#{username}")
    void changePwd(@Param("username")String username, @Param("newPwd")String newPwd);

}
