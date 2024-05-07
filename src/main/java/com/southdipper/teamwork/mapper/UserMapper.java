package com.southdipper.teamwork.mapper;

import com.southdipper.teamwork.pojo.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Select("select * from user where username=#{username}")
    User getByUserName(String username);

    @Insert("insert into user(username,password,email,createTime,updateTime) " +
            "values(#{username},#{password},#{email},now(),now())")
    void register(@Param("username")String username, @Param("password")String password,@Param("email")String email);

    @Update("update user set nickname=#{nickname},email=#{email},update_time=now() where id=#{id}")
    void updateMsg(User user);

    @Update("update user set password=#{newPwd},update_time=now() where id=#{id}")
    void changePwd(@Param("username")String username, @Param("newPwd")String newPwd);
}
