package com.starcord.main.controllers;

import com.starcord.main.dtos.Messages.MessageRequest;
import com.starcord.main.dtos.Messages.MessageResponse;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.Instant;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Handle Group Chats
     * Client sends to: /app/group/{groupId}
     */
    @MessageMapping("/group/{groupId}")
    public void processGroupMessage(@DestinationVariable String groupId,
                                    @Payload MessageRequest request,
                                    Principal principal) {


        MessageResponse response = new MessageResponse();
        response.setAuthorID(Long.parseLong(principal.getName()));
        response.setTimestamp(Instant.now());
        response.setContent(request.getContent());
        response.setChannelID(request.getChannelId());
        response.setMessageID(123456);

        // Save to Database
        // messageService.save(message);

        // 3. Broadcast to everyone subscribed to this group's topic
        messagingTemplate.convertAndSend("/topic/group.%s".formatted(groupId), response);

        System.out.printf("Group Message sent to %s by %s%n", groupId, principal.getName());
    }

    /**
     * Handle Direct Messages
     * Client sends to: /app/chat.private
     */
    @MessageMapping("/chat.private")
    public void processPrivateMessage(@Payload MessageRequest request,
                                      Principal principal) {

        MessageResponse response = new MessageResponse();
        response.setAuthorID(Long.parseLong(principal.getName()));
        response.setTimestamp(Instant.now());
        response.setContent(request.getContent());
        response.setChannelID(request.getChannelId());
        response.setMessageID(123456);

        // messageService.save(message);

        // Send specifically to the recipient's private queue
        messagingTemplate.convertAndSendToUser(
                String.valueOf(response.getAuthorID()),
                "/queue/messages",
                response
        );

        System.out.printf("DM sent from %s to %d%n", principal.getName(), response.getChannelID());
    }
}