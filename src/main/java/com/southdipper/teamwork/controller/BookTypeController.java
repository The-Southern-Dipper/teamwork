package com.southdipper.teamwork.controller;

import com.southdipper.teamwork.pojo.BookType;
import com.southdipper.teamwork.pojo.Result;
import com.southdipper.teamwork.service.BookTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.southdipper.teamwork.util.JwtUtil;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookTypeController {

    @Autowired
    BookTypeService bookTypeService;

    //添加书籍类型
    @PostMapping("/addType")
    public Result addType(String name) {
        bookTypeService.addType(name);
        return Result.success();
    }

    //获取书籍类型
    @GetMapping("/getTypes")
    public Result getTypes() {
        List<BookType> bookTypeList = bookTypeService.getTypes();
        return Result.success(bookTypeList);
    }
}
