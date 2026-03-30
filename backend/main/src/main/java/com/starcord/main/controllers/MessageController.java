package com.starcord.main.controllers;

import com.starcord.main.annotations.RateLimit;
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

    @RateLimit(limit = 100)
    @GetMapping("/{channelID}")
    public @ResponseBody ListOfMessages getMessages(@PathVariable long channelID) {
        return messageService.getAllMessages(channelID);
    }

    /**
     * Get history for a channel
     * @param channelID
     * @param page
     * @param size
     * @param ascending
     * @param before
     * @param after
     * @return
     */
    @RateLimit(limit = 100)
    @GetMapping("/history/{channelID}")
    public @ResponseBody ListOfMessages getHistory(
            @PathVariable long channelID,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "true") boolean ascending,
            @RequestParam(required = false) Long before,
            @RequestParam(required = false) Long after
    ) {
        Sort sort = ascending
                ? Sort.by("timestamp").ascending()
                : Sort.by("timestamp").descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return messageService.getMessages(channelID, pageable, before, after);
    }
}
