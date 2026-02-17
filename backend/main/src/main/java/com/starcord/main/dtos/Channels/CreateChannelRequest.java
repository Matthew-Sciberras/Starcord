package com.starcord.main.dtos.Channels;

import com.starcord.main.emuns.ChannelType;

public class CreateChannelRequest {
    private String name;
    private ChannelType channelType;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public ChannelType getChannelType() { return channelType; }
    public void setChannelType(ChannelType channelType) { this.channelType = channelType; }
}
