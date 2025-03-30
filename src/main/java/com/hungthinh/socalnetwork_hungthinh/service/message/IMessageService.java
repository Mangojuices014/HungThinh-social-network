package com.hungthinh.socalnetwork_hungthinh.service.message;

import com.hungthinh.socalnetwork_hungthinh.dto.request.MessageSendRequest;
import com.hungthinh.socalnetwork_hungthinh.dto.response.MessageResponse;

import java.util.List;

public interface IMessageService {
    List<MessageResponse> getMessages(String senderUsername, String recipientUsername);
    MessageResponse addMessage(MessageSendRequest msg, String senderUsername);
}
