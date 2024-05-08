package com.southdipper.teamwork.controller;

import com.southdipper.teamwork.pojo.BookSell;
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
    public Result userReverse(){
        List<Order> orderList=orderService.getByUserId();
        return Result.success(orderList);
    }

    @PostMapping("/bookBuyer")
    public Result bookBuyer(Integer bookId){
        List<BookSell> bookSellList=orderService.getByBookId(bookId);
        return Result.success(bookSellList);
    }

    @PostMapping("/sellList")
    public Result sellList(){
        List<Order> orderList=orderService.getBySeller();
        return Result.success(orderList);
    }

    @PostMapping("/reserve")
    public Result reserve(Integer bookId){
        Order order=orderService.generate(bookId);
        return Result.success(order);
    }
}
