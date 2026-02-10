package com.starcord.main.dtos.Messages;

import java.time.Instant;

public class MessageResponse {
    private long authorID;
    private String content;
    private Instant timestamp;
    private long channelID;
    private long messageID;

    public MessageResponse() {}
    public MessageResponse(String content) { this.content = content; }

    public long getAuthorID() { return authorID; }
    public void setAuthorID(long authorID) { this.authorID = authorID; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }

    public long getChannelID() { return channelID; }
    public void setChannelID(long channelID) { this.channelID = channelID; }

    public long getMessageID() { return messageID; }
    public void setMessageID(long messageID) { this.messageID = messageID; }
}