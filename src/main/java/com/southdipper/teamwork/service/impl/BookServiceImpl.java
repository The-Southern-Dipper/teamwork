package com.southdipper.teamwork.service.impl;

import com.southdipper.teamwork.mapper.BookMapper;
import com.southdipper.teamwork.pojo.Book;
import com.southdipper.teamwork.pojo.SelectRequest;
import com.southdipper.teamwork.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookMapper bookMapper;

    @Override
    public void add(Book book) {
        bookMapper.add(book);
    }

    @Override
    public List<Book> search(SelectRequest selectRequest) {
        List<Book> bookList = bookMapper.search(selectRequest);
        return bookList;
    }
}
