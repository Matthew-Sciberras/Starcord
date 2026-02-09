package com.starcord.main.controllers;

import com.starcord.main.dtos.Channels.ChannelResponse;
import com.starcord.main.dtos.Channels.CreateChannelRequest;
import com.starcord.main.services.ChannelService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/channels")
public class ChannelController {
    private final ChannelService channelService;

    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    /**
     * @param CreateChannelRequest
     * @return ChannelResponse
     */
    @PostMapping("/create")
    public @ResponseBody ChannelResponse create(@RequestBody CreateChannelRequest request) {
        return channelService.createChannel(request);
    }
}
