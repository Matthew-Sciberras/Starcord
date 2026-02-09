package com.starcord.main.mappers;

import com.starcord.main.dtos.Channels.ChannelResponse;
import com.starcord.main.models.Channel;
import com.starcord.main.models.User;

public class ChannelMapper {

    public static ChannelResponse convertToResponse(Channel channel) {
        ChannelResponse response = new ChannelResponse();
        response.setName(channel.getName());
        response.setChannelID(channel.getId());
        response.setCreatedAt(channel.getCreatedAt());
        response.setCreatorID(channel.getCreator().getID());

        for(User user : channel.getUsers()) {
            response.addMember(user.getID());
        }

        return response;
    }
}
