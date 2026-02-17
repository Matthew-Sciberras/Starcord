package com.starcord.main.repositories;

import com.starcord.main.models.RefreshToken;
import com.starcord.main.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    boolean existsByToken(String token);
    Optional<RefreshToken> findByToken(String token);
    List<RefreshToken> findAllByUser(User user);
    Optional<RefreshToken> findByUserAndDeviceID(User user, String deviceID);
}
