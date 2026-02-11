package com.starcord.main.controllers;

import com.starcord.main.dtos.Channels.AddMemberRequest;
import com.starcord.main.dtos.Channels.ChannelResponse;
import com.starcord.main.dtos.Channels.CreateChannelRequest;
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
     * @param CreateChannelRequest
     * @return ChannelResponse
     */
    @PostMapping("/create")
    public @ResponseBody ChannelResponse create(@RequestBody CreateChannelRequest request) {
        return channelService.createChannel(request);
    }

    @PostMapping("/add/{channelID}")
    public SuccessResponse add(@PathVariable long channelID, @RequestBody AddMemberRequest request) {
        channelService.addMember(channelID, request.getUserID());
        return new SuccessResponse("Successfully added user");
    }

    @GetMapping("/get/{channelID}")
    public @ResponseBody ChannelResponse get(@PathVariable long channelID) {
        if(!channelService.isInChannel(channelID)) {
            throw new UnauthorizedException("You do not have access to this channel");
        }
        return channelService.getChannelData(channelID);
    }
}
