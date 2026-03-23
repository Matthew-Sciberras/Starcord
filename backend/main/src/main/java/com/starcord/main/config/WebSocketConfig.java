package com.starcord.main.config;

import com.starcord.main.security.JwtService;
import com.starcord.main.security.WebSocketAuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtService jwtService;

    public WebSocketConfig(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue");

        // Outbound prefix for sending to specific users
        config.setUserDestinationPrefix("/user");

        // Inbound prefix for messages hitting your @MessageMapping controllers
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        // Registers interceptor as a guard for all incoming STOMP messages
        registration.interceptors(new WebSocketAuthInterceptor(jwtService));
    }
}