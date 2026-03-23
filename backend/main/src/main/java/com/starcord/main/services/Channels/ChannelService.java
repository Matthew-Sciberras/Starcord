package com.starcord.main.services.Channels;

import com.starcord.main.dtos.Channels.ChannelResponse;
import com.starcord.main.dtos.Channels.CreateChannelRequest;
import com.starcord.main.dtos.Channels.ListOfChannels;
import com.starcord.main.enums.ChannelRole;
import com.starcord.main.enums.ChannelType;
import com.starcord.main.exceptions.BadRequestException;
import com.starcord.main.exceptions.NotFoundException;
import com.starcord.main.exceptions.TooManyMembersException;
import com.starcord.main.exceptions.UnauthorizedException;
import com.starcord.main.mappers.ChannelMapper;
import com.starcord.main.models.Channel;
import com.starcord.main.models.ChannelMember;
import com.starcord.main.models.User;
import com.starcord.main.repositories.ChannelRepository;
import com.starcord.main.services.Auth.CustomUserDetailsService;
import com.starcord.main.utils.AuthUtils;
import com.starcord.main.utils.IdUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        if(request.getChannelType() != ChannelType.DM && channelName == null) {
            throw new BadRequestException("Channel name is required if not a DM");
        }
        User user = authUtils.getCurrentUser();
        Channel channel = new Channel();
        channel.setId(idUtils.generateId());
        channel.setName(channelName);
        channel.setCreatedAt(Instant.now());
        channel.setCreator(user);
        channel.addMember(user, ChannelRole.OWNER);
        channel.setChannelType(request.getChannelType());

        // Adding members
        if(request.getMembers() != null && !request.getMembers().isEmpty()) {
            request.getMembers().forEach(memberID -> {
                User member = userDetailsService.loadUserByID(memberID);
                channel.addMember(member, ChannelRole.MEMBER);
            });
        }

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

    public void addMembers(long channelID, Set<Long> memberIDs) {
        Channel channel = channelRepository.findById(channelID).orElseThrow(() -> new NotFoundException("Channel not found"));
        addMemberChecks(authUtils.getCurrentUser(), channel);
        memberIDs.forEach(memberID -> {
            User user = userDetailsService.loadUserByID(memberID);
            channel.addMember(user, ChannelRole.MEMBER);
        });
        channelRepository.save(channel);
    }

    public void addMemberChecks(User user, Channel channel) {
        if(!channel.getAdminsAndOwners().contains(user)) {
            throw new UnauthorizedException("You must be an owner or admin to add users");
        }

        if(channel.getMembers().size() >= channel.getChannelType().getMaxMembers()) {
            throw new TooManyMembersException("You have already hit the maximum number of members. The maximum for this type of channel is %d".formatted(channel.getChannelType().getMaxMembers()));
        }
    }

    public boolean isChannelCreator(Channel channel, User user) {
        return channel.getCreator().equals(user);
    }

    public boolean isInChannel(long channelID, long userID) {
        return channelRepository.isUserInChannel(channelID, userID);
    }

    public boolean isInChannel(long channelID) {
        User user = authUtils.getCurrentUser();
        return channelRepository.isUserInChannel(channelID, user.getID());
    }

    public ChannelResponse getChannelData(long channelID) {
        Channel channel = channelRepository.findById(channelID).orElseThrow(() -> new NotFoundException("Channel not found"));
        return ChannelMapper.convertToResponse(channel);
    }

    private ListOfChannels getFilteredChannels(List<ChannelType> types) {
        User user = authUtils.getCurrentUser();
        List<Channel> sortedChannels = channelRepository.findUserChannelsSorted(user, types);
        List<ChannelResponse> responseList = sortedChannels.stream()
                .map(ChannelMapper::convertToResponse)
                .collect(Collectors.toList());

        ListOfChannels channels = new ListOfChannels();
        channels.setChannels(responseList);
        channels.setTimestamp(Instant.now());
        return channels;
    }

    public ListOfChannels getAllConversations() {
        return getFilteredChannels(List.of(ChannelType.DM, ChannelType.GROUP, ChannelType.CHANNEL));
    }

    public ListOfChannels getUserChats() {
        return getFilteredChannels(List.of(ChannelType.DM, ChannelType.GROUP));
    }

    public ListOfChannels getUserChannels() {
        return getFilteredChannels(List.of(ChannelType.CHANNEL));
    }

    @Transactional // No need to save, transactional auto-saves
    public void updateLastMessage(Channel channel) {
        channel.setLastMessage();
    }

    @Transactional
    public void setLastMessage(Channel channel, Instant timestamp) {
        channel.setLastMessageAt(timestamp);
    }

    public Channel getChannelWithMembers(Long id) {
        return channelRepository.findByIdWithMembers(id)
                .orElseThrow(() -> new NotFoundException("Channel not found"));
    }
}
