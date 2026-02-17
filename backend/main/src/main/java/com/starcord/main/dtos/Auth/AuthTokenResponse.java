package com.starcord.main.dtos.Auth;

public class AuthTokenResponse {
    private String token;
    private long createdAt;
    private long expiresAt;

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }

    public long getExpiresAt() { return expiresAt; }
    public void setExpiresAt(long expiresAt) { this.expiresAt = expiresAt; }
}
