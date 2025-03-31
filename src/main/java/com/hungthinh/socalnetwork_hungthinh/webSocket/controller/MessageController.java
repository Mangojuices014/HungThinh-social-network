package com.hungthinh.socalnetwork_hungthinh.webSocket.controller;

import com.hungthinh.socalnetwork_hungthinh.dto.MessageCaller;
import com.hungthinh.socalnetwork_hungthinh.dto.UserDto;
import com.hungthinh.socalnetwork_hungthinh.dto.request.MessageSendRequest;
import com.hungthinh.socalnetwork_hungthinh.exception.ResourceNotFoundException;
import com.hungthinh.socalnetwork_hungthinh.service.message.IMessageService;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import com.hungthinh.socalnetwork_hungthinh.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import javax.naming.AuthenticationException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private static final Logger log = LoggerFactory.getLogger(MessageController.class);
    private final SimpMessagingTemplate messagingTemplate;
    private final IMessageService messageService;
    private final IUserService userService;
    private final ModelMapper modelMapper;

    @MessageMapping("/user.sendMessage")
    @SendToUser("/topic/reply")
    public Map<String, Object> sendMessage(@Payload MessageSendRequest msg, Principal principal) throws AuthenticationException {
        if (principal == null) {
            throw new AuthenticationException("User not authenticated");
        }

        String senderUsername = principal.getName();

        log.info("Sending message to {}", senderUsername);

        if (msg.getRecipientUsername().equals(senderUsername)) {
            throw new IllegalArgumentException("Cannot send message to yourself");
        }

        var message = messageService.addMessage(msg, principal.getName());
        var user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Can not find user with username: "+principal.getName()));

        Map<String, Object> map = new HashMap<>();
        map.put("group", getGroupName(principal.getName(), msg.getRecipientUsername()));
        map.put("message", message);
        map.put("senderUser", modelMapper.map(user, UserDto.class));

        messagingTemplate.convertAndSendToUser(msg.getRecipientUsername(), "/queue/messages", map);
        return map;
    }

    @MessageMapping("/user.loadMessages")
    @SendToUser("/topic/caller")
    public MessageCaller getMessages(@Payload String recipientUsername, Principal principal){
        String currentUsername = principal.getName();
        String groupName = getGroupName(currentUsername, recipientUsername);

        var messages = messageService.getMessages(currentUsername, recipientUsername);

        return new MessageCaller(groupName, messages);
    }

    private String getGroupName(String username, String recipientUsername){
        int result = username.compareToIgnoreCase(recipientUsername);
        if(result > 0)
            return username+"-"+recipientUsername;
        return recipientUsername+"-"+username;
    }

}

