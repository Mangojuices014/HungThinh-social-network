package com.hungthinh.socalnetwork_hungthinh.service.message;

import com.hungthinh.socalnetwork_hungthinh.dto.request.MessageSendRequest;
import com.hungthinh.socalnetwork_hungthinh.dto.response.MessageResponse;
import com.hungthinh.socalnetwork_hungthinh.exception.ResourceNotFoundException;
import com.hungthinh.socalnetwork_hungthinh.model.Message;
import com.hungthinh.socalnetwork_hungthinh.model.User;
import com.hungthinh.socalnetwork_hungthinh.repository.MessageRepository;
import com.hungthinh.socalnetwork_hungthinh.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService implements IMessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final Logger logger = LoggerFactory.getLogger(MessageService.class);


    @Override
    public List<MessageResponse> getMessages(String senderUsername, String recipientUsername) {
        List<Message> messages = messageRepository.getMessages(senderUsername, recipientUsername);
        return messages.stream()
                .map(msg -> modelMapper.map(msg, MessageResponse.class))
                .toList();
    }

    @Override
    public MessageResponse addMessage(MessageSendRequest msg, String senderUsername) {
        User recipientUser = userRepository.findByUsername(msg.getRecipientUsername())
                .orElseThrow(() -> {
                    logger.error("Recipient Username not found: {}", msg.getRecipientUsername());
                    return new ResourceNotFoundException("Recipient Username not found: " + msg.getRecipientUsername());
                });

        User senderUser = userRepository.findByUsername(senderUsername)
                .orElseThrow(() -> {
                    logger.error("Sender Username not found: {}", senderUsername);
                    return new ResourceNotFoundException("Sender username not found: " + senderUsername);
                });

        Message message = new Message();
        message.setSenderUsername(senderUsername);
        message.setRecipientUsername(msg.getRecipientUsername());
        message.setContent(HtmlUtils.htmlEscape(msg.getContent()));
        message.setCreatedDate(Instant.now());
        message.setSenderUser(senderUser);
        message.setRecipientUser(recipientUser);

        message = messageRepository.save(message);
        return modelMapper.map(message, MessageResponse.class);
    }
}

