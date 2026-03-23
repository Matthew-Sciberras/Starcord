package com.starcord.main.security;

import org.jspecify.annotations.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class WebSocketAuthInterceptor implements ChannelInterceptor {

    private final JwtService jwtService;

    public WebSocketAuthInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Message<?> preSend(@NonNull Message<?> message, @NonNull MessageChannel channel) {
        // MessageHeaderAccessor to wrap the message and access STOMP headers
        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        System.out.printf("Attempting to connect to endpoint %s headers: %s and payload: %s%n", message.toString(), message.getHeaders(), message.getPayload());

        // Care only about the CONNECT frame (the initial handshake)
        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {

            // Get the "Authorization" header sent from the Angular client
            String authHeader = accessor.getFirstNativeHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);

                if (jwtService.isTokenValid(token)) {
                    // Extract user info and create a Principal
                    String userID = String.valueOf(jwtService.extractUserID(token));
                    Principal principal = new StompPrincipal(userID);

                    // Attach user to session
                    accessor.setUser(principal);

                    System.out.printf("User with user ID: %s has connected %n", userID);
                } else {
                    throw new MessageDeliveryException("Invalid credentials, connection denied.");
                }
            } else {
                throw new MessageDeliveryException("No authorization token found.");
            }
        }

        return message;
    }
}