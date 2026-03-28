package com.starcord.main.mappers;

import com.starcord.main.dtos.General.SuccessResponse;
import com.starcord.main.dtos.Messages.ChatMessage;
import com.starcord.main.dtos.Messages.MessageRequest;
import com.starcord.main.dtos.Messages.MessageResponse;
import com.starcord.main.models.Message;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;

public class MessageMapper {

    private static final ObjectMapper mapper = new ObjectMapper();

    // JSON mappers
    public static String convertToJSON(MessageResponse messageResponseDTO) {
        return mapper.writeValueAsString(messageResponseDTO);
    }

    public static MessageResponse convertToResponse(Message message) {
        MessageResponse response = new MessageResponse();
        response.setContent(message.getContent());
        response.setMessageID(message.getId());
        response.setTimestamp(message.getTimestamp());
        response.setChannelID(message.getChannel().getId());
        response.setAuthorID(message.getAuthor().getID());
        return response;
    }

    public static SuccessResponse convertToSuccessResponse(Message message, String tempId) {
        MessageResponse messageResponse = convertToResponse(message);
        messageResponse.setTempId(tempId);
        return new SuccessResponse(
                "Message sent successfully to channel: %s".formatted(message.getChannel()),
                200,
                messageResponse
        );
    }

    public static ChatMessage convertToChatMessage(MessageRequest request, String authorId) {
        ChatMessage response = new ChatMessage();
        response.setContent(request.getContent());
        response.setChannelId(request.getChannelId());
        response.setTimestamp(Instant.now());
        response.setAuthorId(Long.parseLong(authorId));
        return response;
    }
}
