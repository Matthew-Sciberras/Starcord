package com.starcord.main.services;

import com.starcord.main.dtos.Channels.ChannelResponse;
import com.starcord.main.dtos.Channels.CreateChannelRequest;
import com.starcord.main.dtos.General.SuccessResponse;
import com.starcord.main.mappers.ChannelMapper;
import com.starcord.main.models.Channel;
import com.starcord.main.models.User;
import com.starcord.main.repositories.ChannelRepository;
import com.starcord.main.utils.AuthUtils;
import com.starcord.main.utils.IdUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ChannelService {
    private final AuthUtils authUtils;
    private final ChannelRepository channelRepository;
    private final CustomUserDetailsService userDetailsService;
    private final IdUtils idUtils;

    public ChannelService(AuthUtils authUtils, ChannelRepository channelRepository, CustomUserDetailsService userDetailsService, IdUtils idUtils) {
        this.authUtils = authUtils;
        this.channelRepository = channelRepository;
        this.userDetailsService = userDetailsService;
        this.idUtils = idUtils;
    }

    public Channel getChannelByID(Long id) {
        return channelRepository.getReferenceById(id);
    }

    public ChannelResponse createChannel(CreateChannelRequest request) {
        String channelName = request.getName();
        User user = authUtils.getCurrentUser();
        Channel channel = new Channel();
        channel.setId(idUtils.generateId());
        channel.setName(channelName);
        channel.setCreatedAt(Instant.now());
        channel.setCreator(user);
        channel.addUser(user);

        channelRepository.save(channel);
        return ChannelMapper.convertToResponse(channel);
    }

    public void addMember(long channelID, long userID) {
        Channel channel = channelRepository.getReferenceById(channelID);
        User user = userDetailsService.loadUserByID(userID);
        channel.addUser(user);
        channelRepository.save(channel);
    }

    public boolean isInChannel(long channelID, long userID) {
        Channel channel = channelRepository.getReferenceById(channelID);
        User user = userDetailsService.loadUserByID(userID);
        return channel.getUsers().contains(user);
    }

    public boolean isInChannel(long channelID) {
        Channel channel = channelRepository.getReferenceById(channelID);
        return channel.getUsers().contains(authUtils.getCurrentUser());
    }

    public ChannelResponse getChannelData(long channelID) {
        Channel channel = channelRepository.getReferenceById(channelID);
        return ChannelMapper.convertToResponse(channel);
    }
}
