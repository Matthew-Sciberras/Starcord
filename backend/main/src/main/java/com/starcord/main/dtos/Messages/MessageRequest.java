package com.starcord.main.dtos.Messages;

public class MessageRequest {
    private String content;

    public MessageRequest() {}
    public MessageRequest(String content) { this.content = content; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
