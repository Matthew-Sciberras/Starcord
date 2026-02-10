package com.starcord.main.services;

import com.starcord.main.dtos.Messages.MessageRequest;
import com.starcord.main.dtos.Messages.MessageResponse;
import com.starcord.main.mappers.MessageMapper;
import com.starcord.main.models.Message;
import com.starcord.main.models.User;
import com.starcord.main.repositories.MessageRepository;
import com.starcord.main.utils.AuthUtils;
import com.starcord.main.utils.IdUtils;
import com.starcord.main.utils.TimeUtils;
import com.starcord.main.websocket.WebSocketService;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class MessageService {

    private final AuthUtils authUtils;
    private final ChannelService channelService;
    private final MessageRepository messageRepository;
    private final IdUtils idUtils;
    private final WebSocketService webSocketService;

    public MessageService(AuthUtils authUtils, ChannelService channelService, MessageRepository messageRepository, IdUtils idUtils, WebSocketService webSocketService) {
        this.authUtils = authUtils;
        this.channelService = channelService;
        this.messageRepository = messageRepository;
        this.idUtils = idUtils;
        this.webSocketService = webSocketService;
    }

    public MessageResponse createMessage(MessageRequest messageRequest) throws Exception{
        User user = authUtils.getCurrentUser();
        long messageID = idUtils.generateId();
        Message message = new Message();
        message.setId(messageID);
        message.setAuthor(user);
        message.setChannel(channelService.getChannelByID(messageRequest.getChannelID()));
        message.setContent(messageRequest.getContent());
        message.setTimestamp(Instant.now());

        MessageResponse response = MessageMapper.convertToResponse(message);

        messageRepository.save(message);
        webSocketService.sendMessage(response);

        return response;
    }
}
