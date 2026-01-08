package com.example.detectioncar.util;

public class TokenInfo {
    private String username;
    private long createdAtMillis;

    public TokenInfo(String username, long createdAtMillis) {
        this.username = username;
        this.createdAtMillis = createdAtMillis;
    }

    public String getUsername() {
        return username;
    }

    public long getCreatedAtMillis() {
        return createdAtMillis;
    }
}
