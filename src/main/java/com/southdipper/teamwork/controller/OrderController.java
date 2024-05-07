package com.southdipper.teamwork.controller;

import com.southdipper.teamwork.pojo.Order;
import com.southdipper.teamwork.pojo.Result;
import com.southdipper.teamwork.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
 负责人：杨宝烨
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/getInfo")
    public Result getInfo(String userId){
        List<Order> orderList=orderService.getByUserId(userId);
        return Result.success(orderList);
    }
}
