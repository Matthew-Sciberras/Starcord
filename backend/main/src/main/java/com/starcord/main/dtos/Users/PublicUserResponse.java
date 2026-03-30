package com.starcord.main.dtos.Users;

import com.starcord.main.enums.UserPresence;

public class PublicUserResponse {
    private long userID;
    private String username;
    private String displayName;
    private String profilePicture;
    private long createdAt;
    private UserPresence presence;

    public long getUserID() { return userID; }
    public void setUserID(long userID) { this.userID = userID; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public String getProfilePicture() { return profilePicture; }
    public void setProfilePicture(String profilePicture) { this.profilePicture = profilePicture; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }

    public UserPresence getPresence() { return presence; }
    public void setPresence(UserPresence presence) { this.presence = presence; }
}
