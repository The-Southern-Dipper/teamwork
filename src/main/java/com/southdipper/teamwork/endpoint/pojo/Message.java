package com.southdipper.teamwork.endpoint.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private Integer messageType;
    private String Authorization;
    private Integer recieverId;
    private Integer contentType;
    private String content;
}
