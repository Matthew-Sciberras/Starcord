package com.starcord.main.controllers;

import com.starcord.main.dtos.Messages.MessageRequest;
import com.starcord.main.dtos.Messages.MessageResponse;
import com.starcord.main.services.MessageService;
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
     * @body MessageRequest
     * @param channelID
     * @return MessageResponse
     * @throws Exception
     */
    @PostMapping("/{channelID}")
    public @ResponseBody MessageResponse sendMessage(@RequestBody MessageRequest message) throws Exception{
        return messageService.createMessage(message);
    }

    @GetMapping("/ping")
    public String ping() {

        return "Pong!";
    }
}
