package com.starcord.main.dtos.Messages;

import java.time.Instant;
import java.util.List;

//TODO: Add Pagination
public class ListOfMessages {
    private long timestamp;
    private long channelID;
    private List<MessageResponse> messages;

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public long getChannelID() { return channelID; }
    public void setChannelID(long channelID) { this.channelID = channelID; }

    public List<MessageResponse> getMessages() { return messages; }
    public void setMessages(List<MessageResponse> messages) { this.messages = messages; }
}
