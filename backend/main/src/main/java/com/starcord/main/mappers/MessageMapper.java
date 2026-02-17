package com.starcord.main.mappers;

import com.starcord.main.dtos.Messages.MessageResponse;
import com.starcord.main.models.Message;
import tools.jackson.databind.ObjectMapper;

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
        response.setTimestamp(message.getTimestamp().getEpochSecond());
        response.setChannelID(message.getChannel().getId());
        response.setAuthorID(message.getAuthor().getID());
        return response;
    }
}
