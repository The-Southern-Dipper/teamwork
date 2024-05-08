package com.southdipper.teamwork.service;

import com.southdipper.teamwork.pojo.Order;

import java.util.List;

public interface OrderService {
    List<Order> getByUserId(Integer userId);
    List<Order> getByBookId(Integer bookId);
    void generate(Order order);
}
