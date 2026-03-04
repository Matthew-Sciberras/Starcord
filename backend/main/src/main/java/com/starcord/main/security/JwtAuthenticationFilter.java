package com.starcord.main.security;

import com.starcord.main.services.Auth.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, CustomUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        System.out.printf("JwtAuthenticationFilter running for request: %s%n", request.getRequestURI());
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String email;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("Invalid Header");
            filterChain.doFilter(request, response); // no token, continue chain
            return;
        }

        jwt = authHeader.substring(7); // remove "Bearer "
        if (!jwtService.isAccessToken(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!jwtService.isTokenValid(jwt)) {
            filterChain.doFilter(request, response); // invalid token, continue chain (Spring will reject later)
            return;
        }

        email = jwtService.extractEmail(jwt);
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load user details
            System.out.println("Load user details");
            CustomUserDetails userDetails = userDetailsService.loadUserByUsername(email);
            System.out.printf("User Details: %d%n", userDetails.getUserID());
            System.out.printf("Authorities: %s%n", userDetails.getAuthorities());
            // Create authentication token
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

            // Set authentication in context
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
    }
}
