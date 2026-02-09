package com.starcord.main.services;

import com.starcord.main.models.Channel;
import com.starcord.main.repositories.ChannelRepository;
import org.springframework.stereotype.Service;

@Service
public class ChannelService {
    private final ChannelRepository channelRepository;

    public ChannelService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    public Channel getChannelByID(Long id) {
        return channelRepository.getReferenceById(id);
    }
}
