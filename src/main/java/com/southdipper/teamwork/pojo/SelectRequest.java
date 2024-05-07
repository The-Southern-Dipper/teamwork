package com.southdipper.teamwork.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectRequest {
    public static Integer TIME_ORDER_ASC = 0;  //时间正序排序
    public static Integer TIME_ORDER_DESC = 1; //时间倒序排序
    public static Integer PRICE_ORDER_ASC = 2;  //价格正序
    public static Integer PRICE_ORDER_DESC = 3; //价格倒序


    private String content;   //文本搜索
    private Double priceStart;  //大于该价格
    private Double priceEnd;  //小于等于该价格
    private Integer typeId;  //按类型ID查询
    private Integer orderRequest = 1; //排序要求,默认时间倒序
    private Integer pageNumber = 0;    //页码默认0,该值为索引从0开始,可改
    private Integer bookNumber = 10;    //每页书本数默认10,可改
}
