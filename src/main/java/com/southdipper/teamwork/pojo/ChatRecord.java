package com.southdipper.teamwork.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRecord {
    @JsonIgnore
    private Integer id;
    @JsonIgnore
    private Integer connectionId;
    private Integer senderId;
    private Integer recieverId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime sendTime;
    private Integer contentType;
    private String content;
}
