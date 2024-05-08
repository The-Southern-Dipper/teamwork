drop database if exists second_hand;

create database if not exists second_hand;

use second_hand;

set names 'utf8mb4';

create table user
(
    id int unsigned primary key auto_increment comment '用户ID',
    username varchar(20) not null comment '用户名',
    password varchar(50) not null comment '密码',
    nickname varchar(20) default '' comment '昵称',
    email varchar(128) default '' comment '邮箱',
    user_img varchar(128) default '' null comment '头像',
    update_time datetime not null comment '更新时间',
    create_time datetime not null comment '创建时间',
    power tinyint not null default 1 comment '权限'
)default character set utf8mb4 comment '用户表';

create table book_type
(
    id int unsigned primary key auto_increment comment '类别ID',
    name varchar(20) not null unique comment '类别名'
)default character set utf8mb4 comment '书籍类别';

create table book
(
    id int unsigned primary key auto_increment comment '书籍ID',
    name varchar(50) not null comment '书籍名称',
    type_id int unsigned not null comment '类别',
    price decimal(12, 2) not null comment '价格',
    isbn varchar(20) not null comment 'ISBN号',
    img varchar(128) null comment '头像',
    detail varchar(500) default '' null comment '书籍描述',
    release_time datetime not null comment '上架时间',
    seller_id int unsigned not null comment '卖家ID',
    purchased int not null comment '是否已被购买'
)default character set utf8mb4 comment '待售书籍';

create table `order`
(
    id int unsigned primary key auto_increment comment '订单ID',
    user_id int unsigned not null comment '用户ID',
    book_id int unsigned not null comment '书籍ID',
    pay_time datetime not null comment '下单时间',
    address varchar(100) not null comment '收货地址',
    status tinyint not null comment '订单状态'
)default character set utf8mb4 comment '订单';

create table `connection`
(
    id int unsigned primary key auto_increment comment '连接ID',
    user1_id int unsigned not null comment '用户1的ID',
    user2_id int unsigned not null comment '用户2的ID'
)default character set utf8mb4 comment '聊天连接';

create table `record`
(
    connect_id int unsigned not null unique,
    sender_id int unsigned not null comment '发送方ID',
    reciever_id int unsigned not null comment '接收方ID',
    number int unsigned comment '记录编号',
    type int unsigned not null default 1 comment '记录类型',
    content varchar(150) not null comment '记录'
)default character set utf8mb4 comment '聊天记录';

insert into book_type (name) values('全部');