package com.starcord.main.dtos.Channels;

import java.time.Instant;
import java.util.List;

public class ListOfChannels {
    private Instant timestamp;
    private List<ChannelResponse> channels;

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }

    public List<ChannelResponse> getChannels() { return channels; }
    public void setChannels(List<ChannelResponse> channels) { this.channels = channels; }
}

