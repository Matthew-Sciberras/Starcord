package com.starcord.main.handlers;

import com.starcord.main.services.WebHookService;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


@Component
public class SocketConnectionHandler extends TextWebSocketHandler {

    private final WebHookService webHookService;

    public SocketConnectionHandler(WebHookService webHookService) {
        this.webHookService = webHookService;
    }

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception{
        webHookService.connect(session);
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) throws Exception {
        webHookService.disconnect(session, status);
    }
}
