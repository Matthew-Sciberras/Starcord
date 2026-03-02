package com.starcord.main.mappers;

import com.starcord.main.dtos.Auth.LoginPublicResponse;
import com.starcord.main.dtos.Auth.LoginResponse;

public class AuthMapper {
    public static LoginPublicResponse convertToPublicResponse(LoginResponse loginResponse) {
        LoginPublicResponse response = new LoginPublicResponse();
        response.setAccessToken(loginResponse.getAccessToken());
        response.setDisplayName(loginResponse.getDisplayName());
        response.setUsername(loginResponse.getUsername());
        response.setEmail(loginResponse.getEmail());
        response.setUserID(loginResponse.getUserID());
        return response;
    }
}
