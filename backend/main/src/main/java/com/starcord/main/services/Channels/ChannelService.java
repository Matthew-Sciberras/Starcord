package com.starcord.main.services.Channels;

import com.starcord.main.dtos.Channels.ChannelResponse;
import com.starcord.main.dtos.Channels.CreateChannelRequest;
import com.starcord.main.emuns.ChannelRole;
import com.starcord.main.exceptions.NotFoundException;
import com.starcord.main.exceptions.TooManyMembersException;
import com.starcord.main.exceptions.UnauthorizedException;
import com.starcord.main.mappers.ChannelMapper;
import com.starcord.main.models.Channel;
import com.starcord.main.models.User;
import com.starcord.main.repositories.ChannelRepository;
import com.starcord.main.services.Auth.CustomUserDetailsService;
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
        return channelRepository.findById(id).orElseThrow(() -> new NotFoundException("Channel not found"));
    }

    public ChannelResponse createChannel(CreateChannelRequest request) {
        String channelName = request.getName();
        User user = authUtils.getCurrentUser();
        Channel channel = new Channel();
        channel.setId(idUtils.generateId());
        channel.setName(channelName);
        channel.setCreatedAt(Instant.now());
        channel.setCreator(user);
        channel.addMember(user, ChannelRole.OWNER);
        channel.setChannelType(request.getChannelType());

        channelRepository.save(channel);
        return ChannelMapper.convertToResponse(channel);
    }

    public void addMember(long channelID, long userID) {
        Channel channel = channelRepository.findById(channelID).orElseThrow(() -> new NotFoundException("Channel not found"));
        User user = userDetailsService.loadUserByID(userID);
        addMemberChecks(authUtils.getCurrentUser(), channel);
        channel.addMember(user, ChannelRole.MEMBER);
        channelRepository.save(channel);
    }

    public void addMemberChecks(User user, Channel channel) {
        channel.getAdminsAndOwners().forEach(currentUser -> {
            System.out.println(user.getUsername());
        });
        if(!channel.getAdminsAndOwners().contains(user)) {
            throw new UnauthorizedException("You must be an owner or admin to add users");
        }

        if(channel.getMembers().size() >= channel.getChannelType().getMaxMembers()) {
            throw new TooManyMembersException("You have already hit the maximum number of members. The maximum for this type of channel is " + channel.getChannelType().getMaxMembers());
        }
    }

    public boolean isChannelCreator(Channel channel, User user) {
        return channel.getCreator().equals(user);
    }

    public boolean isInChannel(long channelID, long userID) {
        Channel channel = channelRepository.findById(channelID).orElseThrow(() -> new NotFoundException("Channel not found"));
        User user = userDetailsService.loadUserByID(userID);
        return channel.getMembers().stream()
                .anyMatch(member -> member.getUser().equals(user));
    }

    public boolean isInChannel(long channelID) {
        Channel channel = channelRepository.findById(channelID).orElseThrow(() -> new NotFoundException("Channel not found"));
        User user = authUtils.getCurrentUser();
        return channel.getMembers().stream()
                .anyMatch(member -> member.getUser().equals(user));
    }

    public ChannelResponse getChannelData(long channelID) {
        Channel channel = channelRepository.findById(channelID).orElseThrow(() -> new NotFoundException("Channel not found"));
        return ChannelMapper.convertToResponse(channel);
    }
}
