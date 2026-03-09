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

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
}
