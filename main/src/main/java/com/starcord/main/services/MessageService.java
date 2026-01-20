package com.starcord.main.services;

import com.starcord.main.dtos.MessageDTO;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final TimeService timeService;
    private final WebHookService webHookService;
    private final IdService idService;

    public MessageService(TimeService timeService, WebHookService webHookService, IdService idService) {
        this.timeService = timeService;
        this.webHookService = webHookService;
        this.idService = idService;
    }

    public MessageDTO createMessage(MessageDTO messageDTO) throws Exception{
        messageDTO.setTimestamp(timeService.getCurrentTimestamp());
        webHookService.sendMessage(messageDTO);
        return messageDTO;
    }
}
