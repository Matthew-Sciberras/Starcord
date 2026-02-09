package com.starcord.main.mappers;

import com.starcord.main.dtos.Messages.MessageResponse;
import tools.jackson.databind.ObjectMapper;

public class MessageMapper {

    private final ObjectMapper mapper = new ObjectMapper();

    // JSON mappers
    public String convertToJSON(MessageResponse messageResponseDTO) {
        return mapper.writeValueAsString(messageResponseDTO);
    }
}
