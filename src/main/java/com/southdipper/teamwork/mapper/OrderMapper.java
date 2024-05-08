package com.southdipper.teamwork.mapper;

import com.southdipper.teamwork.pojo.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper {

    //通过用户id查询自己的订单列表
    @Select("select * from order where user_id=#{userId}")
    List<Order> getByUserId(Integer userId);

    //通过书籍id查找该书的预订者列表和对应订单id
    @Select("select (user_id,id) from order where book_id=#{bookId}")
    List<Order> getByBookId(Integer bookId);

    //生成订单
    @Insert("insert into order(user_id,book_id,pay_time,address,status)"+
            "values(#{userId},#{bookId},#{payTime},#{address},#{status})")
    void generate(Order order);



}
