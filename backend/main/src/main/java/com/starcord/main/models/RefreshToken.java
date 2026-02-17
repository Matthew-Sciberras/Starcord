package com.starcord.main.models;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "refreshtokens")
public class RefreshToken {
    @Id
    private String id;

    @ManyToOne
    private User user;

    @Column
    private String token;

    @Column(name = "expires_at")
    private Instant expiresAt;

    @Column
    private boolean revoked = false;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "device_id")
    private String deviceID;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }


    public Instant getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Instant expiresAt) { this.expiresAt = expiresAt; }

    public boolean isRevoked() { return revoked; }
    public void setRevoked(boolean revoked) { this.revoked = revoked; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public String getDeviceID() { return deviceID; }
    public void setDeviceID(String deviceID) { this.deviceID = deviceID; }
}
