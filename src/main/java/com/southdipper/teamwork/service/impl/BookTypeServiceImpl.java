package com.southdipper.teamwork.service.impl;

import com.southdipper.teamwork.mapper.BookTypeMapper;
import com.southdipper.teamwork.pojo.BookType;
import com.southdipper.teamwork.service.BookTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookTypeServiceImpl implements BookTypeService {

    @Autowired
    BookTypeMapper bookTypeMapper;

    @Override
    public void addType(String name) {
        bookTypeMapper.addType(name);
    }

    @Override
    public List<BookType> getTypes() {
        return bookTypeMapper.getTypes();
    }
}
