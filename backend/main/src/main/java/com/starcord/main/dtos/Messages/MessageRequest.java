package com.starcord.main.dtos.Messages;

public class MessageRequest {
    private String content;
    private long channelId;
    private String tempId; // Used for the pre-sending of messages on the frontend

    public MessageRequest() {}

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public long getChannelId() { return channelId; }
    public void setChannelId(long channelId) { this.channelId = channelId; }

    public String getTempId() { return tempId; }
    public void setTempId() { this.tempId = tempId;
    }
}
