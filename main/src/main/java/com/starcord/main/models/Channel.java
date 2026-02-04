package com.starcord.main.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "channels")
public class Channel {
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "created_at", nullable = false)
    private long createdAt;

    @Column
    private String image;

    @ManyToOne
    private User creator;

    @ManyToMany
    @JoinTable(
            name = "channel_users",
            joinColumns = @JoinColumn(name = "channel_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL)
    private Set<Message> messages;

}
