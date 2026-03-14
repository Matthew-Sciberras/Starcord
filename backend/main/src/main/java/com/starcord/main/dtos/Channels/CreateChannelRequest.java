package com.starcord.main.dtos.Channels;

import com.starcord.main.enums.ChannelType;

import java.util.Set;

public class CreateChannelRequest {
    private String name;
    private ChannelType channelType;
    private Set<Long> members;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public ChannelType getChannelType() { return channelType; }
    public void setChannelType(ChannelType channelType) { this.channelType = channelType; }

    public Set<Long> getMembers() { return members; }
    public void setMembers(Set<Long> members) { this.members = members; }
    public void addMember(Long member) { this.members.add(member); }
}
