package com.hungthinh.socalnetwork_hungthinh.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageSendRequest {
    private String content;
    private String recipientUsername;
}
