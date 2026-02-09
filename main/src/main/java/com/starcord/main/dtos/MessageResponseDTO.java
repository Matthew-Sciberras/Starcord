package com.starcord.main.dtos;

import com.starcord.main.models.User;

public class MessageResponseDTO {
    private long authorID;
    private String content;
    private long timestamp;
    private long channelID;
    private long messageID;

    public MessageResponseDTO() {}
    public MessageResponseDTO(String content) { this.content = content; }

    public long getAuthorID() { return authorID; }
    public void setAuthorID(long authorID) { this.authorID = authorID; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public long getChannelID() { return channelID; }
    public void setChannelID(long channelID) { this.channelID = channelID; }

    public long getMessageID() { return messageID; }
    public void setMessageID(long messageID) { this.messageID = messageID; }
}