package com.southdipper.teamwork.controller;

import com.southdipper.teamwork.pojo.Book;
import com.southdipper.teamwork.pojo.BookSell;
import com.southdipper.teamwork.pojo.Order;
import com.southdipper.teamwork.pojo.Result;
import com.southdipper.teamwork.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 负责人：杨宝烨
 */
@RestController
@RequestMapping("/order")
@Validated
@CrossOrigin(origins = "*")
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("/userReverse")
    public Result userReverse(){
        List<Order> orderList=orderService.getByUserId();
        return Result.success(orderList);
    }

    @GetMapping("/bookBuyer")
    public Result bookBuyer(Integer id){
        List<BookSell> bookSellList=orderService.getByBookId(id);
        return Result.success(bookSellList);
    }

    @GetMapping("/sellList")
    public Result sellList(){
        List<Book> orderList=orderService.getBySeller();
        return Result.success(orderList);
    }

    @PostMapping("/reserve")
    public Result reserve(Integer id){
        orderService.generate(id);
        return Result.success();
    }

    @PostMapping("/confirm")
    public Result confirm(Integer id){
        orderService.confirm(id);
        return Result.success();
    }
}
