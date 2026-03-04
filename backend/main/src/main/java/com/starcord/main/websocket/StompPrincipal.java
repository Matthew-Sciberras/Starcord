package com.starcord.main.websocket;

import java.security.Principal;

public record StompPrincipal(String email) implements Principal {
    @Override
    public String getName() {
        return email();
    }
}
