package com.starcord.main.security;

import com.starcord.main.dtos.General.ErrorResponse;
import java.io.IOException;
import java.time.Instant;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                         @NonNull AuthenticationException authException) throws IOException {

        // Retrieve the error type set in the Filter
        String errorType = (String) request.getAttribute("auth_error");

        String message = "Authentication failed";

        if ("EXPIRED".equals(errorType)) {
            message = "JWT has expired, please generate a new one via the /refresh endpoint";
        } else if ("INVALID".equals(errorType)) {
            message = "The provided token is malformed or invalid";
        } else if ("MISSING".equals(errorType)) {
            message = "Authorization header is missing";
        }

        ErrorResponse error = new ErrorResponse("UNAUTHORIZED", message, 401, Instant.now());

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(objectMapper.writeValueAsString(error));
    }
}
