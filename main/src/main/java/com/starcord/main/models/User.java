package com.starcord.main.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column
    private String password;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "joined_at", nullable = false)
    private long joinedAt;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "mfa_enabled")
    private boolean mfa;
    @ManyToMany(mappedBy = "users")
    private Set<Channel> channels;
}
