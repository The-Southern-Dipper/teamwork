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
    public List<Order> getByUserId(String userId) {
        return orderMapper.getByUserId(userId);
    }
}
