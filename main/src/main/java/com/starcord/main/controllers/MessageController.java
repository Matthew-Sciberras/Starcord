package com.starcord.main.controllers;

import com.starcord.main.dtos.Messages.ListOfMessages;
import com.starcord.main.dtos.Messages.MessageRequest;
import com.starcord.main.dtos.Messages.MessageResponse;
import com.starcord.main.services.Messages.MessageService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public @ResponseBody MessageResponse sendMessage(@RequestBody MessageRequest message, @PathVariable long channelID) throws Exception{
        return messageService.createMessage(message, channelID);
    }

    @GetMapping("/{channelID}")
    public @ResponseBody ListOfMessages getMessages(@PathVariable long channelID) {
        return messageService.getAllMessages(channelID);
    }

    @GetMapping("/history/{channelID}")
    public @ResponseBody ListOfMessages getHistory(
            @PathVariable long channelID,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "true") boolean ascending,
            @RequestParam(required = false) Long before,
            @RequestParam(required = false) Long after
    ) {
        Sort sort = ascending ? Sort.unsorted().ascending() : Sort.unsorted().descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return messageService.getMessages(channelID, pageable);
    }
}
