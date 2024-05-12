package com.southdipper.teamwork.service.impl;

import com.southdipper.teamwork.mapper.BookMapper;
import com.southdipper.teamwork.mapper.OrderMapper;
import com.southdipper.teamwork.pojo.Book;
import com.southdipper.teamwork.pojo.BookSell;
import com.southdipper.teamwork.pojo.Order;
import com.southdipper.teamwork.service.OrderService;
import com.southdipper.teamwork.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;

    //通过用户id查询自己的订单列表
    @Override
    public List<Order> getByUserId() {
        Integer userId=ThreadLocalUtil.getId();
        return orderMapper.getByUserId(userId);
    }

    //通过书籍id查找该书的预订者和对应订单id
    @Override
    public List<BookSell> getByBookId(Integer bookId) {
        return orderMapper.getByBookId(bookId);
    }

    //通过书籍id查找卖家的出售列表
    @Override
    public List<Book> getBySeller() {
        Integer sellerId=ThreadLocalUtil.getId();
        return orderMapper.getBySeller(sellerId);
    }

    //预定书籍
    @Override
    public void generate(Integer bookId) {
        Order order = new Order();
        order.setBookId(bookId);
        order.setUserId(ThreadLocalUtil.getId());
        order.setStatus(1);
        order.setPayTime(LocalDateTime.now());
    }

    //确认订单
    @Override
    public void confirm(Integer id) {
        Integer bookId=orderMapper.findBookId(id);
        orderMapper.cancel(3,bookId,id);
        orderMapper.confirm(2,id);
        orderMapper.changePurchased(1,bookId);
        orderMapper.delete(id);
    }
}
