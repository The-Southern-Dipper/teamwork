package com.southdipper.teamwork.mapper;

import com.southdipper.teamwork.pojo.Book;
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
            " where second_hand.order.book_id=#{bookId} and second_hand.user.id=(select second_hand.order.user_id from second_hand.order where second_hand.order.book_id=#{bookId})")
    List<BookSell> getByBookId(Integer bookId);

    //通过书籍id查找卖家的出售列表
    @Select("select * from second_hand.book where (seller_id=#{sellerId})")
    List<Book> getBySeller(Integer sellerId);

    //预定书籍
    @Insert("insert into second_hand.order(user_id,book_id,book_name,pay_time,address,status)"+
            " values (#{userId},#{bookId},#{bookName},#{payTime},#{address},#{status})")
    void generate(Order order);
    //根据bookId找到对应书名
    @Select("select second_hand.book.name from second_hand.book where id=#{id}")
    String findBookName(Integer id);

    //确认订单
    //确认的订单
    @Update("update second_hand.order set status=#{status} where id=#{id}")
    void confirm(Integer status,Integer id);
    //根据订单id找到bookId
    @Select("select second_hand.order.book_id from second_hand.order where id=#{id}")
    Integer findBookId(Integer id);
    //被取消的订单
    @Update("update second_hand.order set status=#{status} where book_id=#{bookId} and id!=#{id}")
    void cancel(Integer status,Integer bookId,Integer id);
    //修改预售书籍的purchased
    @Update("update second_hand.book set purchased=#{purchased} where second_hand.book.id=#{bookId}")
    void changePurchased(Integer purchased,Integer bookId);
    @Delete("delete from second_hand.book where second_hand.book.id=(select book_id from second_hand.order where second_hand.order.id=#{id})")
    void delete(Integer id);

}
