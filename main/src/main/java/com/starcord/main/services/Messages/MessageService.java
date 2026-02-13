package com.starcord.main.services.Messages;

import com.starcord.main.dtos.Messages.ListOfMessages;
import com.starcord.main.dtos.Messages.MessageRequest;
import com.starcord.main.dtos.Messages.MessageResponse;
import com.starcord.main.mappers.MessageMapper;
import com.starcord.main.models.Channel;
import com.starcord.main.models.Message;
import com.starcord.main.models.User;
import com.starcord.main.repositories.MessageRepository;
import com.starcord.main.services.Channels.ChannelService;
import com.starcord.main.utils.AuthUtils;
import com.starcord.main.utils.IdUtils;
import com.starcord.main.websocket.WebSocketService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

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

    public MessageResponse createMessage(MessageRequest messageRequest, long channelID) throws Exception{
        User user = authUtils.getCurrentUser();
        long messageID = idUtils.generateId();
        Message message = new Message();
        message.setId(messageID);
        message.setAuthor(user);
        message.setChannel(channelService.getChannelByID(channelID));
        message.setContent(messageRequest.getContent());
        message.setTimestamp(Instant.now());

        MessageResponse response = MessageMapper.convertToResponse(message);

        messageRepository.save(message);
        webSocketService.sendMessage(response);

        return response;
    }

    public ListOfMessages getAllMessages(long channelID) {
        Channel channel = channelService.getChannelByID(channelID);
        List<Message> messageList = messageRepository.getAllByChannel(channel);
        Set<MessageResponse> responseSet = new HashSet<MessageResponse>();
        for(Message message : messageList) {
            responseSet.add(MessageMapper.convertToResponse(message));
        }
        ListOfMessages messages = new ListOfMessages();
        messages.setTimestamp(Instant.now());
        messages.setMessages(responseSet);
        messages.setChannelID(channelID);
        return messages;
    }
}
