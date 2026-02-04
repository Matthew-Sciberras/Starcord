package com.starcord.main.mappers;

import com.starcord.main.dtos.MessageDTO;
import com.starcord.main.models.Message;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class MessageMapper {

    private final ObjectMapper mapper;


    public MessageMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    // JSON mappers
    public String convertToJSON(MessageDTO messageDTO) { return mapper.writeValueAsString(messageDTO); }

    // DTO -> Model Mappers
    public Message convertToModel(MessageDTO dto) {
        Message message = new Message();
        return message;
    }

    // Model -> DTO mappers
}
