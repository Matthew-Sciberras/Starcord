package com.starcord.main.models;

import jakarta.persistence.*;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "channel_id", nullable = false)
    private Channel channel;

    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private long timestamp;

    public Message() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContent() { return content; }
    public long getTimestamp() { return timestamp; }
}
