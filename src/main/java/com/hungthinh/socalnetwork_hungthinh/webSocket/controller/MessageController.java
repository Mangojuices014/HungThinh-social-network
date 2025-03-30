package com.hungthinh.socalnetwork_hungthinh.webSocket.controller;

import com.hungthinh.socalnetwork_hungthinh.dto.MessageCaller;
import com.hungthinh.socalnetwork_hungthinh.dto.UserDto;
import com.hungthinh.socalnetwork_hungthinh.dto.request.MessageSendRequest;
import com.hungthinh.socalnetwork_hungthinh.exception.ResourceNotFoundException;
import com.hungthinh.socalnetwork_hungthinh.service.message.IMessageService;
import org.apache.coyote.BadRequestException;
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

    private final SimpMessagingTemplate messagingTemplate;
    private final IMessageService messageService;
    private final IUserService userService;
    private final ModelMapper modelMapper;

    @MessageMapping("/user.sendMessage")
    public void sendMessage(@Payload MessageSendRequest msg, Principal principal) throws AuthenticationException {
        // 1. Kiểm tra principal
        if (principal == null) {
            throw new AuthenticationException("User not authenticated");
        }

        String senderUsername = principal.getName();

        // 2. Kiểm tra gửi cho chính mình
        if (msg.getRecipientUsername().equals(senderUsername)) {
            throw new IllegalArgumentException("Cannot send message to yourself");
        }

        // 3. Xử lý logic nghiệp vụ
        var message = messageService.addMessage(msg, senderUsername);
        var senderUser = userService.findByUsername(senderUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + senderUsername));

        // 4. Tạo response object
        Map<String, Object> response = new HashMap<>();
        response.put("group", getGroupName(senderUsername, msg.getRecipientUsername()));
        response.put("message", message);
        response.put("senderUser", modelMapper.map(senderUser, UserDto.class));

        // 5. Gửi message đến người nhận
        messagingTemplate.convertAndSendToUser(
                msg.getRecipientUsername(),
                "/queue/messages",
                response
        );

        // 6. Gửi phản hồi cho người gửi (tuỳ chọn)
        messagingTemplate.convertAndSendToUser(
                senderUsername,
                "/queue/messages",
                response
        );
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

