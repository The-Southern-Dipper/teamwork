package com.southdipper.teamwork.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Integer id;
    private String name;
    private Integer typeId;
    private Double price;
    private String isbn;
    private String img;
    private String detail;
    private String releaseTime;
    private Integer sellerId;
    private Integer purchased = 0;  //0代表未售
    private String sellerName;
}
