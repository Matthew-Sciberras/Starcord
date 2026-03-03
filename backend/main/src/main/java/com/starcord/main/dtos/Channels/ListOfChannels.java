package com.starcord.main.dtos.Channels;

import java.util.List;

public class ListOfChannels {
    private long timestamp;
    private List<ChannelResponse> channels;

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public List<ChannelResponse> getChannels() { return channels; }
    public void setChannels(List<ChannelResponse> channels) { this.channels = channels; }
}

