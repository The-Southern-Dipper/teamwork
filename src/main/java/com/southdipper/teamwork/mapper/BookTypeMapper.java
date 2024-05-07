package com.southdipper.teamwork.mapper;

import com.southdipper.teamwork.pojo.BookType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookTypeMapper {

    //添加书籍类型
    void addType(String name);

    //查看书籍类型
    List<BookType> getTypes();
}
