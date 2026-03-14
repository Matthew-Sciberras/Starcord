package com.starcord.main.mappers;

import com.starcord.main.dtos.Auth.SignupResponse;
import com.starcord.main.dtos.Users.PublicUserResponse;
import com.starcord.main.models.User;

public class UserMapper {
    public static SignupResponse convertToDTO(User user) {
        SignupResponse dto = new SignupResponse();
        dto.setUserID(user.getID());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setDisplayName(user.getDisplayName());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }

    public static PublicUserResponse convertToPublicDTO(User user) {
        PublicUserResponse response = new PublicUserResponse();
        response.setUserID(user.getID());
        response.setDisplayName(user.getDisplayName());
        response.setUsername(user.getUsername());
        response.setProfilePicture(user.getProfilePicture());
        response.setCreatedAt(user.getCreatedAt());
        return response;
    }
}
