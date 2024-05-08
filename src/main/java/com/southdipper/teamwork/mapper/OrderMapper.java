package com.southdipper.teamwork.mapper;

import com.southdipper.teamwork.pojo.BookSell;
import com.southdipper.teamwork.pojo.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {

    //通过用户id查询自己的订单列表
    @Select("select * from second_hand.order where user_id=#{userId}")
    List<Order> getByUserId(Integer userId);

    //通过书籍id查找该书的预订者和对应订单id
    @Select("select second_hand.order.id,second_hand.user.nickname,second_hand.user.email from second_hand.order join second_hand.user" +
            " where second_hand.order.book_id=#{bookId}")
    List<BookSell> getByBookId(Integer bookId);

    //通过书籍id查找卖家的出售列表
    @Select("select * from second_hand.order where book_id=(select id from second_hand.book where (seller_id=#{sellerId}))")
    List<Order> getBySeller(Integer sellerId);

    //预定书籍
    @Insert("insert into second_hand.order(user_id,book_id,pay_time,address,status)"+
            " values (#{userId},#{bookId},#{payTime},#{address},#{status})")
    void generate(Order order);

    //确认订单
    //确认的订单
    @Update("update second_hand.order set status=#{status} where id=#{id}")
    void confirm(Integer status,Integer id);
    //被取消的订单
    @Update("update second_hand.order set status=#{status} where second_hand.order.book_id=" +
            "(select book_id from " +
            "(select second_hand.order.book_id from second_hand.order where book_id=#{id})a )" +
            " and id!=#{id}")
    void cancel(Integer status,Integer id);
    //修改预售书籍的purchased
    @Update("update second_hand.book set purchased=#{purchased}" +
            " where second_hand.book.id=(select second_hand.order.book_id from second_hand.order where second_hand.order.id=#{id})")
    void changePurchased(Integer purchased,Integer id);
    @Delete("delete from second_hand.book where second_hand.book.id=(select book_id from second_hand.order where second_hand.order.id=#{id})")
    void delete(Integer id);

}
