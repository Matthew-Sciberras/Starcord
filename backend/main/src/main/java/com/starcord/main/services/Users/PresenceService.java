package com.starcord.main.services.Users;

import com.starcord.main.enums.UserPresence;
import com.starcord.main.models.User;
import com.starcord.main.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class PresenceService {
    private final UserRepository userRepository;

    public PresenceService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserPresence getUserPresence(long userId) {
        return userRepository.getUserById(userId).getPresence();
    }

    public void updatePresence(long userId, UserPresence presence) {
        User user = userRepository.getUserById(userId);
        user.setPresence(presence);
        userRepository.save(user);
    }
}
