package com.starcord.main.dtos;

import com.starcord.main.models.User;

public class MessageRequestDTO {
    private String content;
    private long channelID;

    public MessageRequestDTO() {}
    public MessageRequestDTO(String content) { this.content = content; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public long getChannelID() { return channelID; }
    public void setChannelID(long channelID) { this.channelID = channelID; }
}
