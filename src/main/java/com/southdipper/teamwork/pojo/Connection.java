package com.southdipper.teamwork.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Connection {
    private Integer id;
    private Integer user1Id;
    private Integer user2Id;
    private boolean user1Online;
    private boolean user2Online;
    private Integer user1Unread;
    private Integer user2Unread;
    private Integer latestContentType;
    private String latestContent;
    private LocalDateTime createTime;
}
