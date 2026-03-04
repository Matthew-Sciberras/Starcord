package com.starcord.main.websocket;

import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


@Component
public class SocketConnectionHandler extends TextWebSocketHandler {

    private final WebSocketService webSocketService;

    public SocketConnectionHandler(WebSocketService webHookService) {
        this.webSocketService = webHookService;
    }

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception{
        webSocketService.connect(session);
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) throws Exception {
        webSocketService.disconnect(session, status);
    }
}
