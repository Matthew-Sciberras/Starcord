package com.starcord.main.dtos.Messages;

public class MessageRequest {
    private String from;
    private String text;

    public String text() {
        return text;
    }

    public MessageRequest setText(String text) {
        this.text = text;
        return this;
    }

    public String from() {
        return from;
    }

    public MessageRequest setFrom(String from) {
        this.from = from;
        return this;
    }


    /*
    public MessageRequest() {}
    public MessageRequest(String content) { this.content = content; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

     */
}
