package com.starcord.main.websocket;

import com.starcord.main.dtos.Messages.ChatMessage;
import com.starcord.main.services.Channels.ChannelService;
import com.starcord.main.services.Messages.MessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChannelService channelService;
    private final MessageService messageService;

    public ChatController(SimpMessagingTemplate messagingTemplate, ChannelService channelService, MessageService messageService) {
        this.messagingTemplate = messagingTemplate;
        this.channelService = channelService;
        this.messageService = messageService;
    }

    @MessageMapping("/chat.send")
    public void sendMessage(@Payload ChatMessage message, Principal principal) {
        // This actually returns email but the way the Principal class is setup I have to write getName instead
        String email = principal.getName();
        long senderId = 123; // To change
        message.setSenderId(senderId);

        if (!channelService.isInChannel(message.getChannelId(), senderId)) return;

        messageService.save(message);
        messagingTemplate.convertAndSend(
                "/topic/conversation." + message.getChannelId(),
                message
        );
    }
}