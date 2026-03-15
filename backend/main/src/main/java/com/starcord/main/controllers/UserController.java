package com.starcord.main.controllers;

import com.starcord.main.annotations.RateLimit;
import com.starcord.main.dtos.Users.GetUsersRequest;
import com.starcord.main.dtos.Users.PublicUserResponse;
import com.starcord.main.mappers.UserMapper;
import com.starcord.main.models.User;
import com.starcord.main.services.Auth.CustomUserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final CustomUserDetailsService userDetailsService;

    public UserController(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @RateLimit(limit = 50, timeWindowSeconds = 60)
    @GetMapping("/get/{userID}")
    public @ResponseBody PublicUserResponse get(@PathVariable long userID) {
        User user = userDetailsService.loadUserByID(userID);
        return UserMapper.convertToPublicDTO(user);
    }

    @RateLimit(limit=30, timeWindowSeconds = 60)
    @GetMapping("/get")
    public @ResponseBody Set<PublicUserResponse> getMultiple(@RequestBody GetUsersRequest request) {
        return userDetailsService.loadUsersByIDs(request.getUsers()).stream()
                .map(UserMapper::convertToPublicDTO)
                .collect(Collectors.toSet());
    }
}
