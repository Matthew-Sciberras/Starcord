package com.starcord.main.dtos;

public class MessageDTO {
    private String content;
    private long timestamp; // Optional
    private long channelID;

    public MessageDTO() {}
    public MessageDTO(String content) { this.content = content; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public long getChannelID() { return channelID; }
    public void setChannelID(long channelID) { this.channelID = channelID; }
}