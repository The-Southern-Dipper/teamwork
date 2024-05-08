package com.southdipper.teamwork.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Integer id;
    private Integer userId;
    private Integer bookId;
    private LocalDateTime payTime;
    private String address;
    private Integer status; //1.卖家正在处理 2.交易达成 3.卖家将书籍出售给他人
}
