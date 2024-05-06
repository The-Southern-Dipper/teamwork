package com.southdipper.teamwork.service.impl;

import com.southdipper.teamwork.mapper.BookMapper;
import com.southdipper.teamwork.pojo.Book;
import com.southdipper.teamwork.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookMapper bookMapper;

    @Override
    public void add(Book book) {
        bookMapper.add(book);
    }
}
