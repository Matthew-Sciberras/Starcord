package com.starcord.main.controllers;

import com.starcord.main.annotations.RateLimit;
import com.starcord.main.dtos.General.SuccessResponse;
import com.starcord.main.dtos.Users.GetUsersRequest;
import com.starcord.main.dtos.Users.PresenceResponse;
import com.starcord.main.dtos.Users.PresenceUpdateRequest;
import com.starcord.main.dtos.Users.PublicUserResponse;
import com.starcord.main.enums.UserPresence;
import com.starcord.main.mappers.UserMapper;
import com.starcord.main.models.User;
import com.starcord.main.services.Auth.CustomUserDetailsService;
import com.starcord.main.services.Users.PresenceService;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final CustomUserDetailsService userDetailsService;
    private final PresenceService presenceService;

    public UserController(CustomUserDetailsService userDetailsService, PresenceService presenceService) {
        this.userDetailsService = userDetailsService;
        this.presenceService = presenceService;
    }

    @RateLimit(limit = 50)
    @GetMapping("/{userID}")
    public @ResponseBody PublicUserResponse get(@PathVariable long userID) {
        User user = userDetailsService.loadUserByID(userID);
        return UserMapper.convertToPublicDTO(user);
    }

    // Post mapping to allow for the request body
    @RateLimit(limit=30)
    @PostMapping("/get")
    public @ResponseBody Set<PublicUserResponse> getMultiple(@RequestBody GetUsersRequest request) {
        return userDetailsService.loadUsersByIDs(request.getUsers()).stream()
                .map(UserMapper::convertToPublicDTO)
                .collect(Collectors.toSet());
    }

    //Todo: Do Tousend's idea where invisible actually returns offline
    @RateLimit(limit=30)
    @GetMapping("/{userId}/presence")
    public @ResponseBody PresenceResponse getUserPresence(@PathVariable String userId) {
        UserPresence presence = presenceService.getUserPresence(Long.parseLong(userId));
        return new PresenceResponse(Long.parseLong(userId), presence);
    }

    @RateLimit(limit=30)
    @PatchMapping("/{userId}/presence")
    public @ResponseBody SuccessResponse updatePresence(@PathVariable long userId, @RequestBody PresenceUpdateRequest request) {
        presenceService.updatePresence(userId, request.getStatus());
        return new SuccessResponse("Successfully updated presence to %s".formatted(request.getStatus()));
    }
}
