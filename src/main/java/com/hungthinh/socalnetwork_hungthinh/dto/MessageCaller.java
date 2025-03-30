package com.hungthinh.socalnetwork_hungthinh.dto;

import com.hungthinh.socalnetwork_hungthinh.dto.response.MessageResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageCaller {
    private String group;
    private List<MessageResponse> messages;
}
