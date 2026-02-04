package com.starcord.main.services;

import com.starcord.main.dtos.MessageDTO;
import com.starcord.main.utils.IdUtils;
import com.starcord.main.utils.TimeUtils;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final TimeUtils timeUtils;
    private final WebHookService webHookService;

    public MessageService(TimeUtils timeUtils, WebHookService webHookService) {
        this.timeUtils = timeUtils;
        this.webHookService = webHookService;
    }

    public MessageDTO createMessage(MessageDTO messageDTO) throws Exception{
        messageDTO.setTimestamp(timeUtils.getCurrentTimestamp());
        webHookService.sendMessage(messageDTO);
        return messageDTO;
    }
}
