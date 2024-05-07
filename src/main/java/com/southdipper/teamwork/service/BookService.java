package com.southdipper.teamwork.service;

import com.southdipper.teamwork.pojo.Book;
import com.southdipper.teamwork.pojo.SelectRequest;

import java.util.List;

public interface BookService {

    //添加书籍
    void add(Book book);

    //按要求查找别人的书籍
    List<Book> search(SelectRequest selectRequest);

    //查找待售书籍（按用户ID）
    List<Book> searchFromId(Integer sellerId);
}
