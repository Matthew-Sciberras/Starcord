package com.starcord.main.emuns;

public enum ChannelType {
    DM(2),         // DM between 2 people
    GROUP(20),     // Group Chat max 20 members
    CHANNEL(Integer.MAX_VALUE); // Server channel, unlimited

    private final int maxMembers;

    ChannelType(int maxMembers) {
        this.maxMembers = maxMembers;
    }

    public int getMaxMembers() {
        return maxMembers;
    }
}
