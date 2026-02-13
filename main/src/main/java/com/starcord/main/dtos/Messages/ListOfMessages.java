package com.starcord.main.dtos.Messages;

import java.time.Instant;
import java.util.Set;

//TODO: Add Pagination
public class ListOfMessages {
    private Instant timestamp;
    private long channelID;
    private Set<MessageResponse> messages;

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }

    public long getChannelID() { return channelID; }
    public void setChannelID(long channelID) { this.channelID = channelID; }

    public Set<MessageResponse> getMessages() { return messages; }
    public void setMessages(Set<MessageResponse> messages) { this.messages = messages; }
}
