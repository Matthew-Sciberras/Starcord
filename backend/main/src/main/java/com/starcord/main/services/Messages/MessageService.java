package com.starcord.main.services.Messages;

import com.starcord.main.dtos.Messages.ChatMessage;
import com.starcord.main.dtos.Messages.ListOfMessages;
import com.starcord.main.dtos.Messages.MessageRequest;
import com.starcord.main.dtos.Messages.MessageResponse;
import com.starcord.main.exceptions.ForbiddenException;
import com.starcord.main.exceptions.NotFoundException;
import com.starcord.main.mappers.MessageMapper;
import com.starcord.main.models.Channel;
import com.starcord.main.models.Message;
import com.starcord.main.models.User;
import com.starcord.main.repositories.MessageRepository;
import com.starcord.main.services.Auth.CustomUserDetailsService;
import com.starcord.main.services.Channels.ChannelService;
import com.starcord.main.utils.AuthUtils;
import com.starcord.main.utils.IdUtils;
import com.starcord.main.websocket.WebSocketService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

@Service
public class MessageService {

    private final AuthUtils authUtils;
    private final ChannelService channelService;
    private final CustomUserDetailsService userDetailsService;
    private final MessageRepository messageRepository;
    private final IdUtils idUtils;
    private final WebSocketService webSocketService;

    public MessageService(AuthUtils authUtils, ChannelService channelService, CustomUserDetailsService userDetailsService, MessageRepository messageRepository, IdUtils idUtils, WebSocketService webSocketService) {
        this.authUtils = authUtils;
        this.channelService = channelService;
        this.userDetailsService = userDetailsService;
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
        List<MessageResponse> responseList = messageList.stream()
                .map(MessageMapper::convertToResponse)
                .toList();

        ListOfMessages messages = new ListOfMessages();
        messages.setTimestamp(Instant.now().getEpochSecond());
        messages.setMessages(responseList);
        messages.setChannelID(channelID);
        return messages;
    }

    public ListOfMessages getMessages(long channelID, Pageable pageable, Long before, Long after) {

        Channel channel = channelService.getChannelByID(channelID);

        Page<Message> messagePage;

        if (before != null && after != null) {
            messagePage = messageRepository.findByChannelAndTimestampBetween(channel, Instant.ofEpochSecond(after), Instant.ofEpochSecond(before), pageable);
        } else if (before != null) {
            messagePage = messageRepository.findByChannelAndTimestampBefore(channel, Instant.ofEpochSecond(before), pageable);
        } else if (after != null) {
            messagePage = messageRepository.findByChannelAndTimestampAfter(channel, Instant.ofEpochSecond(after), pageable);
        } else {
            messagePage = messageRepository.findByChannel(channel, pageable);
        }

        List<MessageResponse> responseList = new ArrayList<>();

        for (Message message : messagePage.getContent()) {
            responseList.add(MessageMapper.convertToResponse(message));
        }

        ListOfMessages messages = new ListOfMessages();
        messages.setTimestamp(Instant.now().getEpochSecond());
        messages.setMessages(responseList);
        messages.setChannelID(channelID);

        return messages;
    }

    public Message save(ChatMessage chatMessage) {
        User author = userDetailsService.loadUserByID(chatMessage.getSenderId());

        Channel channel = channelService.getChannelByID(chatMessage.getChannelId());

        if (!channelService.isInChannel(channel.getId(), author.getID())) {
            throw new ForbiddenException("User not in channel");
        }

        Message message = new Message();
        message.setAuthor(author);
        message.setChannel(channel);
        message.setContent(chatMessage.getContent());
        message.setTimestamp(Instant.now());

        return messageRepository.save(message);
    }
}
