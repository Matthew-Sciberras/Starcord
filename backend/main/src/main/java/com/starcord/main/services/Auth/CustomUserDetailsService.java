package com.starcord.main.services.Auth;

import com.starcord.main.security.CustomUserDetails;
import com.starcord.main.exceptions.NotFoundException;
import com.starcord.main.models.User;
import com.starcord.main.repositories.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @NonNull
    public CustomUserDetails loadUserByUsername(@NonNull String email) throws NotFoundException {
        User user = userRepository.findByEmail(email.toLowerCase().trim()).orElseThrow(() -> new NotFoundException("User not found"));
        return new CustomUserDetails(user);
    }

    public User loadUserByEmail(@NonNull String email) throws NotFoundException {
        return userRepository.findByEmail(email.toLowerCase().trim()).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public User loadUserByID(long id) throws NotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public Set<User> loadUsersByIDs(Set<Long> ids) {
        List<User> users = userRepository.findAllByIdIn(ids);
        if (users.size() != ids.size()) {
            throw new NotFoundException("One or more users not found");
        }
        return new HashSet<>(users);
    }
}
