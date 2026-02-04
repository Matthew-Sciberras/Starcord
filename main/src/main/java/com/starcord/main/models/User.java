package com.starcord.main.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column
    private String password;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "created_at", nullable = false)
    private long createdAt;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "mfa_enabled")
    private boolean mfa;

    @ManyToMany(mappedBy = "users")
    private Set<Channel> channels;

    @Column
    private boolean active;

    public Long getID() { return id; }
    public void setID(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }

    public String getProfilePicture() { return profilePicture; }
    public void setProfilePicture(String profilePicture) { this.profilePicture = profilePicture; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public boolean getMfa() { return mfa; }
    public void setMfa(boolean mfa) { this.mfa = mfa; }

    public Set<Channel> getChannels() { return channels; }
    public void setChannels(Set<Channel> channels) { this.channels = channels; }
    public void addChannel(Channel channel) { channels.add(channel); };
    public void removeChannel(Channel channel) { channels.remove(channel); }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
