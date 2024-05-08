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

    @PostMapping("/userReverse")
    public Result userReverse(Integer userId){
        List<Order> orderList=orderService.getByUserId(userId);
        return Result.success(orderList);
    }

    @PostMapping("/bookBuyer")
    public Result bookBuyer(Integer bookId){
        List<Order> orderList=orderService.getByBookId(bookId);
        return Result.success(orderList);
    }

    @PostMapping("/reserve")
    public Result reserve(Order order){
        orderService.generate(order);
        return Result.success();
    }
}
