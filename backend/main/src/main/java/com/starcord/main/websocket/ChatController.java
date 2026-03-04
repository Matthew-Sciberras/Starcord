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
        long senderId = Long.parseLong(principal.getName());
        message.setSenderId(senderId);

        if (!channelService.isInChannel(message.getChannelId(), senderId)) return;

        messageService.save(message);
        messagingTemplate.convertAndSend(
                "/topic/conversation.%d".formatted(message.getChannelId()),
                message
        );
    }
}