package com.starcord.main.controllers;

import com.starcord.main.dtos.MessageRequestDTO;
import com.starcord.main.dtos.MessageResponseDTO;
import com.starcord.main.services.MessageService;
import com.starcord.main.utils.RequestUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Controller for sending messages to a specific channel ID
     * @body MessageRequestDTO
     * @param channelID
     * @return MessageResponseDTO
     * @throws Exception
     */
    @PostMapping("/{channelID}")
    public @ResponseBody MessageResponseDTO sendMessage(@RequestBody MessageRequestDTO message) throws Exception{
        return messageService.createMessage(message);
    }

    @GetMapping("/ping")
    public String ping() {

        return "Pong!";
    }
}
