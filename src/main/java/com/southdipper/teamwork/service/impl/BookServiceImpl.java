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

    //添加待售书籍
    @Override
    public void add(Book book) {
        bookMapper.add(book);
    }

    //查找待售书籍（按需求类）
    @Override
    public List<Book> search(SelectRequest selectRequest) {
        selectRequest.setPageNumber(selectRequest.getPageNumber() * selectRequest.getBookNumber());
        List<Book> bookList = bookMapper.search(selectRequest);
        bookList.forEach(book -> book.setReleaseTime(book.getReleaseTime().substring(0,10)));
        return bookList;
    }

    //查找待售书籍（按用户ID）
    @Override
    public List<Book> searchFromId(Integer sellerId) {
        return bookMapper.searchFromId(sellerId);
    }

    @Override
    public void delete(Integer id) {
        bookMapper.delete(id);
    }

    //获取当前搜索书籍总数
    @Override
    public Integer getNumber(SelectRequest selectRequest) {
        return bookMapper.getNumber(selectRequest);
    }

    //通过bookId获取书籍信息
    @Override
    public Book getBook(Integer bookId) {
        return bookMapper.getBook(bookId);
    }
}
