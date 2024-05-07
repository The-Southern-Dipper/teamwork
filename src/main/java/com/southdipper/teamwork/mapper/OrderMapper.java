package com.southdipper.teamwork.mapper;

import com.southdipper.teamwork.pojo.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper {

    //通过用户名查询自己的订单列表
    @Select("select * from order where user_id=#{userId}")
    List<Order> getByUserId(String userId);

    //生成订单
    @Insert("insert into order(user_id,book_id,pay_time,address,status)"+
            "value(#{userId},#{bookId},now(),#{address},#{status})")
    void generate(Order order);



}
