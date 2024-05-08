package com.southdipper.teamwork.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookSell {
    //用作查看某待售书籍所有预定者的nickname、email和订单id
    private String nickname;
    private String email;
    private Integer orderId;
}
