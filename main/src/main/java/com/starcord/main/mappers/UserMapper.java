package com.starcord.main.mappers;

import com.starcord.main.dtos.Auth.SignupResponse;
import com.starcord.main.models.User;

public class UserMapper {
    public SignupResponse convertToDTO(User user) {
        SignupResponse dto = new SignupResponse();
        dto.setUserID(user.getID());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setDisplayName(user.getDisplayName());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }
}
