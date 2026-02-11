package com.starcord.main.models;

import com.starcord.main.emuns.ChannelRole;
import com.starcord.main.emuns.ChannelType;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChannelMember> members = new HashSet<>();


    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL)
    private Set<Message> messages;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ChannelType channelType;

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

    public Set<ChannelMember> getMembers() {
        return members;
    }

    public void addMember(User user, ChannelRole role) {
        ChannelMember member = new ChannelMember();
        member.setChannel(this);
        member.setUser(user);
        member.setRole(role);
        member.setJoinedAt(Instant.now());

        members.add(member);
    }

    public void removeMember(User user) {
        members.removeIf(member -> member.getUser().equals(user));
    }

    public Set<User> getAdminsAndOwners() {
        return members.stream()
                .filter(m -> m.getRole() == ChannelRole.ADMIN || m.getRole() == ChannelRole.OWNER)
                .map(ChannelMember::getUser)
                .collect(Collectors.toSet());
    }



    public ChannelType getChannelType() { return channelType; }
    public void setChannelType(ChannelType channelType) { this.channelType = channelType; }
}
