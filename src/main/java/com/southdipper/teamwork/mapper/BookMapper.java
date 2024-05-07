package com.southdipper.teamwork.mapper;

import com.southdipper.teamwork.pojo.Book;
import com.southdipper.teamwork.pojo.SelectRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookMapper {

    //添加待售书籍
    public void add(Book book);

    //按要求查询书籍
    public List<Book> search(SelectRequest selectRequest);

    //按用户ID查询待售书籍
    List<Book> searchFromId(Integer sellerId);

    //按书籍 ID 删除书本
    void delete(Integer id);
}
