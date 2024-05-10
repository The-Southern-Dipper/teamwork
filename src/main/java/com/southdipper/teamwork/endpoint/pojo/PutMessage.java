package com.southdipper.teamwork.endpoint.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PutMessage {
    private Integer type;
    private Integer senderId;
    private LocalDateTime sendTime;
    private Integer contentType;
    private String content;
}
