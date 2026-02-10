package com.starcord.main.services;

import com.starcord.main.dtos.Auth.SignupRequest;
import com.starcord.main.dtos.Auth.SignupResponse;
import com.starcord.main.exceptions.EmailInUseException;
import com.starcord.main.exceptions.UsernameInUseException;
import com.starcord.main.mappers.UserMapper;
import com.starcord.main.models.User;
import com.starcord.main.repositories.UserRepository;
import com.starcord.main.utils.IdUtils;
import com.starcord.main.utils.TimeUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignupService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IdUtils idUtils;
    private final UserMapper userMapper = new UserMapper();

    public SignupService(UserRepository userRepository, PasswordEncoder passwordEncoder, IdUtils idUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.idUtils = idUtils;
    }

    @Transactional
    public SignupResponse createUser(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) { throw new EmailInUseException(); }
        if (userRepository.existsByUsername(request.getUsername())) { throw new UsernameInUseException(); }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User();
        user.setID(idUtils.generateId());
        user.setUsername(request.getUsername());
        user.setPassword(encodedPassword);
        user.setDisplayName(request.getDisplayName());
        user.setCreatedAt(TimeUtils.getCurrentTimestamp());
        user.setEmail(request.getEmail());
        user.setMfa(false);
        user.setActive(false);

        userRepository.save(user);
        return userMapper.convertToDTO(user);
    }
}
