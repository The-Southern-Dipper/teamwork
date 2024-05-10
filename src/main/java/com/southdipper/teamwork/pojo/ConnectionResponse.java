package com.southdipper.teamwork.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionResponse {
    private Integer connectionId;
    private Integer chatTargetId;
    private boolean isChatTargetOnline;
    private Integer unreadMessageCount;
    private Integer latestUnreadMessageType;
    private String  latestUnreadMessage;
}
