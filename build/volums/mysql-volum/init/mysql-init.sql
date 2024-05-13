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
    book_name varchar(50) not null comment '书籍名称',
    pay_time datetime not null comment '下单时间',
    address varchar(100) not null comment '收货地址',
    status tinyint not null comment '订单状态'
)default character set utf8mb4 comment '订单';

create table `connection`
(
    id int unsigned primary key auto_increment comment '连接ID',
    user1_id int unsigned not null comment '用户1ID',
    user2_id int unsigned not null comment '用户2ID',
    user1_online tinyint(1) not null default 0 comment '用户1是否在线',
    user2_online tinyint(1) not null default 0 comment '用户2是否在线',
    user1_unread int not null default 0 comment '用户1未读数',
    user2_unread int not null default 0 comment '用户2未读数',
    latest_content_type int comment '最后一条聊天记录类型',
    latest_content varchar(500) comment '最后一条聊天记录',
    create_time datetime not null comment '创建时间'
)character set utf8mb4 comment '聊天连接表';

create table `chat_record`
(
    id int unsigned primary key auto_increment comment '聊天记录ID',
    connection_id int unsigned not null comment '聊天连接的ID',
    sender_id int unsigned not null comment '连接中的发送方ID',
    reciever_id int unsigned not null comment '连接中的接收方ID',
    send_time datetime not null comment '发送时间',
    content_type int not null comment '消息类型',
    content varchar(500) comment '聊天记录'
)character set utf8mb4 comment '聊天记录表';

# 初始化一些数据用于测试
insert into user(id, username, password, nickname, email, user_img, update_time, create_time, power)
values (1, 'Aderversa', 'e10adc3949ba59abbe56e057f20f883e', '不想早自习', '2457699535@qq.com','http://127.0.0.1:8080/images/default.jpg', now(), now(), 0),
       (2, 'miaomiao',  'e10adc3949ba59abbe56e057f20f883e', 'miaomiao',  '1992201300@qq.com','http://127.0.0,1:8080/images/default.jpg', now(), now(), 0);

insert into book(id, name, type_id, price, isbn, img, detail, release_time, seller_id, purchased)
VALUES (1, '高等数学（上）', 4, 49.50, '123456', '1.jpg', '', now(), 1, 0),
       (2, '高等数学（上）', 4, 49.50, '123456', '2.jpg', '', now(), 1, 0),
       (3, '高等数学（下）', 4, 48.50, '123456', '3.jpg', '', now(), 1, 0),
       (4, '高等数学（下）', 4, 48.50, '123456', '4.jpg', '', now(), 1, 0),
       (5, '计算机组成原理', 5, 55.50, '123456', '5.jpg', '', now(), 1, 0),
       (6, 'C语言程序设计', 5, 54.50, '123456', '6.jpg', '', now(), 1, 0),
       (7, '电工学（上）', 6, 47.50, '123456', '7.jpg', '', now(), 1, 0),
       (8, '电工学（下）', 6, 44.50, '123456', '8.jpg', '', now(), 1, 0),
       (9, '电工学（上）', 6, 47.50, '123456', '9.jpg', '', now(), 1, 0),
       (10, '电工学（下）', 4, 46.50, '123456', '10.jpg', '', now(), 1, 0),
       (11, '算法设计与分析基础', 5, 59.50, '123456', '11.jpg', '', now(), 1, 0),
       (12, '算法设计与分析基础', 5, 69.50, '123456', '12.jpg', '', now(), 1, 0),
       (13, '计算机科学概论', 5, 48.50, '123456', '13.jpg', '', now(), 1, 0),
       (14, '计算机科学概论', 5, 47.50, '123456', '14.jpg', '', now(), 1, 0),
       (15, '计算机组成原理', 5, 46.50, '123456', '15.jpg', '', now(), 1, 0),
       (16, '计算机组成原理', 5, 44.50, '123456', '16.jpg', '', now(), 1, 0),
       (17, '计算机组成原理', 5, 47.50, '123456', '17.jpg', '', now(), 1, 0),
       (18, 'C语言程序设计', 5, 39.50, '123456', '18.jpg', '', now(), 1, 0),
       (19, 'C语言程序设计', 5, 43.50, '123456', '19.jpg', '', now(), 1, 0),
       (20, 'C语言程序设计', 5, 41.50, '123456', '20.jpg', '', now(), 1, 0),
       (21, '高等数学（上）', 4, 23.50, '123456', '21.jpg', '', now(), 1, 0),
       (22, '计算机科学概论', 5, 48.50, '123456', '22.jpg', '', now(), 1, 0),
       (23, 'C语言程序设计', 4, 60.50, '123456', '23.jpg', '', now(), 1, 0),
       (24, '计算机科学概论', 5, 42.50, '123456', '24.jpg', '', now(), 1, 0),
       (25, '高等数学（上）', 4, 49.50, '123456', '25.jpg', '', now(), 1, 0),
       (26, '高等数学（下）', 4, 39.50, '123456', '26.jpg', '', now(), 1, 0),
       (27, '高等数学（下）', 4, 36.50, '123456', '27.jpg', '', now(), 1, 0);

insert into book_type(id, name)
VALUES
    (1,'实验报告'),
    (2,'小说'),
    (3,'散文'),
    (4,'数学类'),
    (5,'计算机类'),
    (6,'物理类');

insert into `order`(id, user_id, book_id, book_name, pay_time, address, status)
values (1, 2, 1,  '高等数学（上）' , now(), '广东工业大学教5-525', 1),
       (2, 2, 2,  '高等数学（上）', now(),'广东工业大学教5-525', 1),
       (3, 2, 3,  '高等数学（上）', now(), '广东工业大学教5-525', 1),
       (4, 2, 4,  '高等数学（上）', now(), '广东工业大学教5-525', 1);
