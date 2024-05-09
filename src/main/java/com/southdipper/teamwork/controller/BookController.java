package com.southdipper.teamwork.controller;

import com.southdipper.teamwork.pojo.Book;
import com.southdipper.teamwork.pojo.Result;
import com.southdipper.teamwork.pojo.SelectRequest;
import com.southdipper.teamwork.service.BookService;
import com.southdipper.teamwork.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@Validated
@CrossOrigin(origins = "*")
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
    public Result searchFromId() {
        Integer sellerId = ThreadLocalUtil.getId();
        List<Book> bookList = bookService.searchFromId(sellerId);
        return Result.success(bookList);
    }

    //删除待售书籍(按书籍ID进行删除)
    @PutMapping("/delete")
    public Result delete(Integer id) {
        bookService.delete(id);
        return Result.success();
    }
}
