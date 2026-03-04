package com.starcord.main.websocket;

import java.security.Principal;

public record StompPrincipal(String userID) implements Principal {
    @Override
    public String getName() {
        return userID();
    }
}
