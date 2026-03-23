package com.starcord.main.controllers;

import com.starcord.main.dtos.Messages.MessageRequest;
import com.starcord.main.dtos.Messages.MessageResponse;
import com.starcord.main.enums.ChannelType;
import com.starcord.main.exceptions.BadRequestException;
import com.starcord.main.mappers.MessageMapper;
import com.starcord.main.models.Channel;
import com.starcord.main.models.ChannelMember;
import com.starcord.main.models.Message;
import com.starcord.main.models.User;
import com.starcord.main.services.Channels.ChannelService;
import com.starcord.main.services.Messages.MessageService;
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
    private final MessageService messageService;
    private final ChannelService channelService;

    public ChatController(SimpMessagingTemplate messagingTemplate, MessageService messageService, ChannelService channelService) {
        this.messagingTemplate = messagingTemplate;
        this.messageService = messageService;
        this.channelService = channelService;
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

        Message message = messageService.save(MessageMapper.convertToChatMessage(request, principal.getName()));

        MessageResponse response = MessageMapper.convertToResponse(message);

        Channel channel = channelService.getChannelWithMembers(response.getChannelID());
        // Checks
        if(channel.getChannelType() != ChannelType.DM) {
            throw new BadRequestException("This endpoint should only be used for DM's, please use the appropriate endpoint for your channel type.");
        }

        long recipientId = channel.getMembers().stream()
                .map(ChannelMember::getUser)
                .map(User::getID)
                .filter(id -> !id.equals(response.getAuthorID()))
                .findFirst()
                .orElse(-1L);

        if (recipientId == -1L) {
            System.out.println("CRITICAL: No recipient found in the channel members!");
        }

        // Send specifically to the recipient's private queue
        messagingTemplate.convertAndSendToUser(
                String.valueOf(recipientId),
                "/queue/messages",
                response
        );

        System.out.printf("DM sent from %s to %d%n", principal.getName(), recipientId);
    }
}