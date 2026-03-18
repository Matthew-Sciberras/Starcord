package com.starcord.main.services.Messages;

import com.starcord.main.dtos.Messages.ChatMessage;
import com.starcord.main.dtos.Messages.ListOfMessages;
import com.starcord.main.dtos.Messages.MessageResponse;
import com.starcord.main.exceptions.ForbiddenException;
import com.starcord.main.mappers.MessageMapper;
import com.starcord.main.models.Channel;
import com.starcord.main.models.Message;
import com.starcord.main.models.User;
import com.starcord.main.repositories.MessageRepository;
import com.starcord.main.services.Auth.CustomUserDetailsService;
import com.starcord.main.services.Channels.ChannelService;
import com.starcord.main.utils.IdUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    private final ChannelService channelService;
    private final CustomUserDetailsService userDetailsService;
    private final MessageRepository messageRepository;
    private final IdUtils idUtils;

    public MessageService(ChannelService channelService, CustomUserDetailsService userDetailsService, MessageRepository messageRepository, IdUtils idUtils) {
        this.channelService = channelService;
        this.userDetailsService = userDetailsService;
        this.messageRepository = messageRepository;
        this.idUtils = idUtils;
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

    @Transactional
    public Message save(ChatMessage chatMessage) {
        User author = userDetailsService.loadUserByID(chatMessage.getAuthorId());
        Channel channel = channelService.getChannelByID(chatMessage.getChannelId());

        if (!channelService.isInChannel(channel.getId(), author.getID())) {
            throw new ForbiddenException("User not in channel");
        }

        Message message = new Message();
        message.setId(idUtils.generateId());
        message.setAuthor(author);
        message.setChannel(channel);
        message.setContent(chatMessage.getContent());
        message.setTimestamp(Instant.now());

        messageRepository.save(message);

        channelService.setLastMessage(channel, message.getTimestamp());
        return message;
    }
}
