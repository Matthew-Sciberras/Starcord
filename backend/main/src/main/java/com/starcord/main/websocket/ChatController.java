package com.starcord.main.websocket;

import com.starcord.main.dtos.General.SuccessResponse;
import com.starcord.main.dtos.Messages.ChatMessage;
import com.starcord.main.dtos.Messages.MessageRequest;
import com.starcord.main.dtos.Messages.OutputMessage;
import com.starcord.main.services.Channels.ChannelService;
import com.starcord.main.services.Messages.MessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public SuccessResponse greeting(MessageRequest message) {
        System.out.println("Content: " + message.from());
        return new SuccessResponse("Message sent succesfully");
    }

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public OutputMessage send(MessageRequest message) throws Exception {
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new OutputMessage(message.from(), message.text(), time);
    }

}