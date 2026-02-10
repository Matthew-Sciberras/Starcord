package com.starcord.main.websocket;

import com.starcord.main.dtos.Messages.MessageResponse;
import com.starcord.main.mappers.MessageMapper;
import com.starcord.main.models.Channel;
import com.starcord.main.models.User;
import com.starcord.main.services.ChannelService;
import com.starcord.main.utils.AuthUtils;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;

@Service
public class WebSocketService {
    Map<String, WebSocketConnection> webSocketSessionList = new HashMap<>();

    private final AuthUtils authUtils;
    private final ChannelService channelService;

    public WebSocketService(AuthUtils authUtils, ChannelService channelService) {
        this.authUtils = authUtils;
        this.channelService = channelService;
    }

    public void connect(@NonNull WebSocketSession session) throws Exception{
        User user = authUtils.getCurrentWebhookuser(session);

        WebSocketConnection webSocketConnection = new WebSocketConnection();
        webSocketConnection.setWebSocketSession(session);
        webSocketConnection.setSessionID(session.getId());
        webSocketConnection.setUser(user);
        webSocketSessionList.put(session.getId(), webSocketConnection);
        session.sendMessage(new TextMessage("Connection successful with ID: " + session.getId()));
    }

    public void disconnect(@NonNull WebSocketSession session, @NonNull CloseStatus status) throws Exception {
        webSocketSessionList.remove(session.getId());
    }

    /**
     * Send messages to clients
     * @param MessageResponseDTO
     * @throws Exception
     */
    public void sendMessage(MessageResponse messageResponseDTO) throws Exception {
        TextMessage message = new TextMessage(MessageMapper.convertToJSON(messageResponseDTO));
        System.out.println("Message: " + message.getPayload());
        long channelID = messageResponseDTO.getChannelID();
        Channel channel = channelService.getChannelByID(channelID);
        System.out.println("Found channel with ID: " + channelID);
        Set<User> users = channel.getUsers();
        for (WebSocketConnection webSocketConnection : webSocketSessionList.values()) {
            User user = webSocketConnection.getUser();
            if(users.contains(user)) {
                System.out.println("Attempting to send to " + webSocketConnection.getSessionID() + " with user ID: " + webSocketConnection.getUser().getID());
                webSocketConnection.getWebSocketSession().sendMessage(message);
            }
        }
    }
}
