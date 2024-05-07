package com.southdipper.teamwork.controller;

import com.southdipper.teamwork.pojo.Book;
import com.southdipper.teamwork.pojo.Result;
import com.southdipper.teamwork.pojo.SelectRequest;
import com.southdipper.teamwork.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;

    //添加待售书籍信息
    @PostMapping("/add")
    public Result add(@RequestBody Book book) {
        bookService.add(book);
        return Result.success();
    }

    //查询待售书籍信息(按一要求类SelectRequest)
    @PostMapping("/search")
    public Result search(@RequestBody SelectRequest selectRequest) {
        List<Book> bookList =  bookService.search(selectRequest);
        return Result.success(bookList);
    }

    //查询待售书籍信息(按用户ID查询)
    @PostMapping("/searchFromId")
    public Result searchFromId(Integer sellerId) {
        List<Book> bookList = bookService.searchFromId(sellerId);
        return Result.success(bookList);
    }
}
