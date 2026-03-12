package com.starcord.main.models;

import com.starcord.main.enums.ChannelRole;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "channel_members",
        uniqueConstraints = @UniqueConstraint(columnNames = {"channel_id", "user_id"}))
public class ChannelMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "channel_id")
    private Channel channel;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChannelRole role;

    @Column(name = "joined_at", nullable = false)
    private Instant joinedAt;

    // getters + setters

    public Long getId() { return id; }

    public Channel getChannel() { return channel; }
    public void setChannel(Channel channel) { this.channel = channel; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public ChannelRole getRole() { return role; }
    public void setRole(ChannelRole role) { this.role = role; }

    public Instant getJoinedAt() { return joinedAt; }
    public void setJoinedAt(Instant joinedAt) { this.joinedAt = joinedAt; }
}
