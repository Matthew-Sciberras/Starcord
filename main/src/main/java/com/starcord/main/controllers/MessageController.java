package com.starcord.main.controllers;

import com.starcord.main.dtos.MessageDTO;
import com.starcord.main.services.MessageService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Controller for sending messages to a specific channel ID
     * @body message
     * @param channelID
     * @return MessageDTO
     * @throws Exception
     */
    @PostMapping("/{channelID}")
    public @ResponseBody MessageDTO sendMessage(@RequestBody MessageDTO message) throws Exception{
        return messageService.createMessage(message);
    }
}
