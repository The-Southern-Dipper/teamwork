package com.southdipper.teamwork.service.impl;

import com.southdipper.teamwork.mapper.OrderMapper;
import com.southdipper.teamwork.pojo.Order;
import com.southdipper.teamwork.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;

    @Override
    public List<Order> getByUserId(Integer userId) {
        return orderMapper.getByUserId(userId);
    }

    @Override
    public List<Order> getByBookId(Integer bookId) {
        return orderMapper.getByBookId(bookId);
    }

    @Override
    public void generate(Order order) {
        orderMapper.generate(order);
    }
}
