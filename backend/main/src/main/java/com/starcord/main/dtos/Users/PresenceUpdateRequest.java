package com.starcord.main.dtos.Users;

import com.starcord.main.enums.UserPresence;

public class PresenceUpdateRequest {
    private UserPresence status;

    public UserPresence getStatus() { return status; }
    public void setPresence(UserPresence status) { this.status = status; }
}
