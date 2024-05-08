package com.southdipper.teamwork.service;

import com.southdipper.teamwork.pojo.BookSell;
import com.southdipper.teamwork.pojo.Order;

import java.util.List;

public interface OrderService {
    //通过用户id查询自己的订单列表
    List<Order> getByUserId();

    //通过书籍id查找该书的预订者和对应订单id
    List<BookSell> getByBookId(Integer bookId);

    //通过书籍id查找卖家的出售列表
    List<Order> getBySeller();

    //预定书籍
    void generate(Integer bookId);

    //确认订单
    void confirm(Integer id);
}
