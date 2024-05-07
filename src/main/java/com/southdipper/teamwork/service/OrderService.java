package com.southdipper.teamwork.service;

import com.southdipper.teamwork.pojo.Order;

import java.util.List;

public interface OrderService {
    List<Order> getByUserId(String userId);

}
