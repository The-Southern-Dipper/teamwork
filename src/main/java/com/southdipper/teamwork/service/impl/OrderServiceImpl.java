package com.southdipper.teamwork.service.impl;

import com.southdipper.teamwork.mapper.OrderMapper;
import com.southdipper.teamwork.pojo.BookSell;
import com.southdipper.teamwork.pojo.Order;
import com.southdipper.teamwork.service.OrderService;
import com.southdipper.teamwork.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;

    @Override
    public List<Order> getByUserId() {
        Integer userId=ThreadLocalUtil.getId();
        return orderMapper.getByUserId(userId);
    }

    @Override
    public List<BookSell> getByBookId(Integer bookId) {
        return orderMapper.getByBookId(bookId);
    }

    @Override
    public List<Order> getBySeller() {
        Integer sellerId=ThreadLocalUtil.getId();
        return orderMapper.getBySeller(sellerId);
    }

    @Override
    public Order generate(Integer bookId) {
        Order order = new Order();
        order.setBookId(bookId);
        order.setUserId(ThreadLocalUtil.getId());
        order.setStatus(1);
        order.setPayTime(LocalDateTime.now());
        return orderMapper.generate(order);
    }
}
