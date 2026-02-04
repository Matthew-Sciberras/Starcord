package com.starcord.main.security;

import com.starcord.main.models.User;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final User user;
    private final List<GrantedAuthority> authorities;

    public CustomUserDetails(User user) {
        this.user = user;
        this.authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    @NonNull
    public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }

    @Override
    public @Nullable String getPassword() { return user.getPassword(); }

    @Override
    @NonNull
    public String getUsername() { return user.getEmail(); }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    public String getUserHandle() { return user.getUsername(); }
    public String getDisplayName() { return user.getDisplayName(); }
    public Long getUserID() { return user.getID(); }
    public User getUser() { return user; }
}
