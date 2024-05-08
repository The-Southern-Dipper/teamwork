package com.southdipper.teamwork.mapper;

import com.southdipper.teamwork.pojo.BookSell;
import com.southdipper.teamwork.pojo.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface OrderMapper {

    //通过用户id查询自己的订单列表
    @Select("select * from order where user_id=#{userId}")
    List<Order> getByUserId(Integer userId);

    //通过书籍id查找该书的预订者和对应订单id
    @Select("select (nickname,email,order.id) from (user,order  ) where book_id=#{bookId}")
    List<BookSell> getByBookId(Integer bookId);

    //通过书籍id查找卖家的出售列表
    @Select("select * from order where book_id=(select id from book where (seller_id=#{sellerId}))")
    List<Order> getBySeller(Integer sellerId);

    //预定书籍
    @Insert("insert into order(user_id,book_id,pay_time,address,status)"+
            "values(#{userId},#{bookId},#{payTime},#{address},#{status})")
    Order generate(Order order);

    //确认订单
    @Update("update order set status=2 where id=#{id}")
    Order confirm();


}
