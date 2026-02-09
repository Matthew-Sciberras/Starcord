package com.starcord.main.dtos.Messages;

public class MessageRequest {
    private String content;
    private long channelID;

    public MessageRequest() {}
    public MessageRequest(String content) { this.content = content; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public long getChannelID() { return channelID; }
    public void setChannelID(long channelID) { this.channelID = channelID; }
}
