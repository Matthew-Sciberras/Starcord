package com.starcord.main.dtos.Users;

import com.starcord.main.enums.UserPresence;

public class PresenceResponse {
    private long userId;
    private UserPresence presence;

    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }

    public UserPresence getPresence() { return presence; }
    public void setPresence(UserPresence presence) { this.presence = presence; }

    public PresenceResponse() {}
    public PresenceResponse(long userId, UserPresence presence) {
        this.userId = userId;
        this.presence = presence;
    }
}
