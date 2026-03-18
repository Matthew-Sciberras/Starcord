package com.starcord.main.dtos.Messages;

public class MessageRequest {
    private String content;
    private long channelId;

    public MessageRequest() {}

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public long getChannelId() { return channelId; }
    public void setChannelId(long channelId) { this.channelId = channelId; }
}
