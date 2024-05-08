package com.southdipper.teamwork.service;

import com.southdipper.teamwork.pojo.BookSell;
import com.southdipper.teamwork.pojo.Order;

import java.util.List;

public interface OrderService {
    List<Order> getByUserId();
    List<BookSell> getByBookId(Integer bookId);
    List<Order> getBySeller();
    Order generate(Integer bookId);
}
