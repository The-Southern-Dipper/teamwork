package com.southdipper.teamwork.service;

import com.southdipper.teamwork.pojo.BookType;

import java.util.List;

public interface BookTypeService {

    //添加书籍类型
    public void addType(String name);

    //查看书籍类型
    public List<BookType> getTypes();
}
