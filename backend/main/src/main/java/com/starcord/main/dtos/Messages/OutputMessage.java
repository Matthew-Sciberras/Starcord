package com.starcord.main.dtos.Messages;

public class OutputMessage {
    String from;
    String content;
    String time;

    public OutputMessage(String from, String content, String time) {
        this.from = from;
        this.content = content;
        this.time = time;
    }
}
