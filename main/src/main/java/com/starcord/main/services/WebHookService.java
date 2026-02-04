package com.starcord.main.services;

import com.starcord.main.dtos.MessageDTO;
import com.starcord.main.mappers.MessageMapper;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class WebHookService {
    List<WebSocketSession> webSocketSessionList = Collections.synchronizedList(new ArrayList<>());

    private final MessageMapper messageMapper;

    public WebHookService(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
    }

    public void connect(@NonNull WebSocketSession session) throws Exception{
        webSocketSessionList.add(session);
        session.sendMessage(new TextMessage("Connection successful with ID: " + session.getId()));
    }

    public void disconnect(@NonNull WebSocketSession session, @NonNull CloseStatus status) throws Exception {
        webSocketSessionList.remove(session);
    }

    /**
     * Send messages to clients
     * @param messageDTO
     * @throws Exception
     */
    public void sendMessage(MessageDTO messageDTO) throws Exception {
        TextMessage message = new TextMessage(messageMapper.convertToJSON(messageDTO));
        for (WebSocketSession webSocketSession : webSocketSessionList) {
            webSocketSession.sendMessage(message);
        }
    }
}
