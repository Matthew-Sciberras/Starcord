package com.starcord.main.utils;

import com.starcord.main.models.User;
import com.starcord.main.security.JwtService;
import com.starcord.main.services.CustomUserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class AuthUtils {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public AuthUtils(JwtService jwtService, CustomUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    public User getCurrentUser() {
        String jwt = RequestUtils.getAuthorizationToken();
        String email = jwtService.extractEmail(jwt);
        return userDetailsService.loadUserByEmail(email);
    }

    public User getCurrentWebhookuser(WebSocketSession session) {
        String jwt = session.getHandshakeHeaders().get("Authorization").getFirst().substring(7);;
        String email = jwtService.extractEmail(jwt);
        return userDetailsService.loadUserByEmail(email);
    }
}
