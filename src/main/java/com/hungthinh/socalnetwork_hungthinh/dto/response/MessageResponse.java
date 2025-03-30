package com.hungthinh.socalnetwork_hungthinh.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hungthinh.socalnetwork_hungthinh.webSocket.utils.LocalDateTimeDeserializer;
import com.hungthinh.socalnetwork_hungthinh.webSocket.utils.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {
    private String id;
    private String senderUsername;
    private String recipientUsername;
    private String content;
    private UserResponse senderUser;
    private UserResponse recipientUser;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private Instant createdDate;
}
