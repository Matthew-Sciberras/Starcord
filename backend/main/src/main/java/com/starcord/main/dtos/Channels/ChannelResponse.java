package com.starcord.main.dtos.Channels;

import com.starcord.main.enums.ChannelType;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

public class ChannelResponse {
    private String name;
    private long channelID;
    private String image;
    private Instant createdAt;
    private long creatorID;
    private Set<Long> members;
    private ChannelType channelType;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public long getChannelID() { return channelID; }
    public void setChannelID(long channelID) { this.channelID = channelID; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public long getCreatorID() { return creatorID; }
    public void setCreatorID(long creatorID) { this.creatorID = creatorID; }

    public Set<Long> getMembers() { return members; }
    public void addMember(Long memberID) {
        if(members == null) { members = new HashSet<Long>(); }
        members.add(memberID);
    }
    public void setMembers(Set<Long> members) { this.members = members; }

    public ChannelType getChannelType() { return channelType; }
    public void setChannelType(ChannelType channelType) { this.channelType = channelType; }
}
