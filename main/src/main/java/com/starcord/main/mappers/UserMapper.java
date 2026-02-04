package com.starcord.main.mappers;

import com.starcord.main.dtos.SignupResponseDTO;
import com.starcord.main.models.User;

public class UserMapper {
    public SignupResponseDTO convertToDTO(User user) {
        SignupResponseDTO dto = new SignupResponseDTO();
        dto.setUserID(user.getID());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setDisplayName(user.getDisplayName());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }
}
