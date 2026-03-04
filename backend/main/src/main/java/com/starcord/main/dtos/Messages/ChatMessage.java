package com.starcord.main.dtos.Messages;

import java.time.Instant;

public class ChatMessage {
    private long channelId;
    private String content;
    private long senderId;
    private Instant timestamp;

    public ChatMessage() {}

    // Getters & Setters
    public Long getChannelId() { return channelId; }
    public void setChannelId(Long channelId) { this.channelId = channelId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public long getSenderId() { return senderId; }
    public void setSenderId(long senderId) { this.senderId = senderId; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}