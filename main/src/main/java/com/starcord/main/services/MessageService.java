package com.starcord.main.services;

import com.starcord.main.dtos.MessageRequestDTO;
import com.starcord.main.dtos.MessageResponseDTO;
import com.starcord.main.models.User;
import com.starcord.main.security.JwtService;
import com.starcord.main.utils.AuthUtils;
import com.starcord.main.utils.IdUtils;
import com.starcord.main.utils.RequestUtils;
import com.starcord.main.utils.TimeUtils;
import com.starcord.main.websocket.WebSocketService;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final AuthUtils authUtils;
    private final TimeUtils timeUtils;
    private final IdUtils idUtils;
    private final WebSocketService webHookService;

    public MessageService(AuthUtils authUtils, TimeUtils timeUtils, IdUtils idUtils, WebSocketService webHookService) {
        this.authUtils = authUtils;
        this.timeUtils = timeUtils;
        this.idUtils = idUtils;
        this.webHookService = webHookService;
    }

    public MessageResponseDTO createMessage(MessageRequestDTO messageRequestDTO) throws Exception{
        User user = authUtils.getCurrentUser();
        MessageResponseDTO response = new MessageResponseDTO();
        response.setContent(messageRequestDTO.getContent());
        response.setMessageID(idUtils.generateId());
        response.setTimestamp(timeUtils.getCurrentTimestamp());
        response.setChannelID(messageRequestDTO.getChannelID());
        response.setAuthorID(user.getID());

        webHookService.sendMessage(response);

        return response;
    }
}
