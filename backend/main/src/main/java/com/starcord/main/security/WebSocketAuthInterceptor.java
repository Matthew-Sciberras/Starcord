package com.starcord.main.security;

import com.starcord.main.websocket.StompPrincipal;
import org.jspecify.annotations.Nullable;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
public class WebSocketAuthInterceptor implements HandshakeInterceptor {

    private final JwtService jwtService;

    public WebSocketAuthInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request, ServerHttpResponse response,
            WebSocketHandler wsHandler, Map<String, Object> attributes) {

        System.out.println("Initializing handshake");
        String authHeader = ((ServletServerHttpRequest) request)
                .getServletRequest()
                .getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtService.isTokenValid(token)) {
                String userID = String.valueOf(jwtService.extractUserID(token));
                attributes.put("principal", new StompPrincipal(userID));
                System.out.printf("Handshake initialized for user ID %s%n", userID);
                return true;
            }
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, @Nullable Exception exception) {
        System.out.println("After handshake");
    }

}
