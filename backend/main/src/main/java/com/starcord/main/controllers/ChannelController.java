package com.starcord.main.controllers;

import com.starcord.main.annotations.RateLimit;
import com.starcord.main.dtos.Channels.AddMemberRequest;
import com.starcord.main.dtos.Channels.ChannelResponse;
import com.starcord.main.dtos.Channels.CreateChannelRequest;
import com.starcord.main.dtos.Channels.ListOfChannels;
import com.starcord.main.dtos.General.SuccessResponse;
import com.starcord.main.exceptions.UnauthorizedException;
import com.starcord.main.services.Channels.ChannelService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/channels")
public class ChannelController {
    private final ChannelService channelService;

    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    /**
     * Takes in CreateChannelRequest
     * @return ChannelResponse
     */
    @RateLimit(limit = 10)
    @PostMapping("/create")
    public @ResponseBody ChannelResponse create(@RequestBody CreateChannelRequest request) {
        return channelService.createChannel(request);
    }

    @RateLimit(limit = 25)
    @PostMapping("/add")
    public SuccessResponse add(@RequestBody AddMemberRequest request) {
        channelService.addMembers(request.getChannelId(), request.getMembers());
        return new SuccessResponse("Successfully added user");
    }

    @RateLimit(limit = 50)
    @GetMapping("/{channelID}")
    public @ResponseBody ChannelResponse get(@PathVariable long channelID) {
        if(!channelService.isInChannel(channelID)) {
            throw new UnauthorizedException("You do not have access to this channel");
        }
        return channelService.getChannelData(channelID);
    }

    @RateLimit(limit = 10)
    @GetMapping("/getAll")
    public @ResponseBody ListOfChannels getAll() {
        return channelService.getAllConversations();
    }

    @RateLimit(limit = 10)
    @GetMapping("/getAll/chats")
    public @ResponseBody ListOfChannels getAllChats() {
        return channelService.getUserChats();
    }

    @RateLimit(limit = 10)
    @GetMapping("/getAll/channels")
    public @ResponseBody ListOfChannels getUserChannels() {
        return channelService.getUserChannels();
    }
}
