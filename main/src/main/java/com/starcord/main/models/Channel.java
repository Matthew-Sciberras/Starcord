package com.starcord.main.models;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "channels")
public class Channel {
    @Id
    private Long id;

    @Column(nullable = false, unique = false)
    private String name;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

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

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public User getCreator() { return creator; }
    public void setCreator(User creator) { this.creator = creator; }

    public Set<User> getUsers() { return users; }
    public void addUser(User user) {
        if(users == null) { users = new HashSet<>(); }
        users.add(user);

        if (user.getChannels() == null) { user.setChannels(new HashSet<>()); }
        user.getChannels().add(this);
    }
    public void removeUser(User user) {
        if (users != null) { users.remove(user); }
        if (user.getChannels() != null) { user.getChannels().remove(this); }
    }
}
