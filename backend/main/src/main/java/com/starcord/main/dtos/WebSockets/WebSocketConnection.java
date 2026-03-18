package com.starcord.main.dtos.WebSockets;

import com.starcord.main.models.User;
import org.springframework.web.socket.WebSocketSession;

public class WebSocketConnection {
    private String sessionID;
    private WebSocketSession webSocketSession;
    private User user;

    public String getSessionID() { return sessionID; }
    public void setSessionID(String sessionID) { this.sessionID = sessionID; }

    public WebSocketSession getWebSocketSession() { return webSocketSession; }
    public void setWebSocketSession(WebSocketSession webSocketSession) { this.webSocketSession = webSocketSession; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
