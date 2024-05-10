package com.southdipper.teamwork.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRecord {
    private Integer id;
    private Integer connectionId;
    private Integer senderId;
    private Integer recieverId;
    private LocalDateTime sendTime;
    private Integer contentType;
    private String content;
}
