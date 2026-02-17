package com.avik.microservices.auth_service.controller;

import com.avik.microservices.auth_service.dto.AuthResponse;
import com.avik.microservices.auth_service.dto.LoginRequest;
import com.avik.microservices.auth_service.dto.RegisterRequest;
import com.avik.microservices.auth_service.dto.UserResponse;
import com.avik.microservices.auth_service.service.AuthService;
import com.avik.microservices.auth_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @GetMapping("/users")
    public List<UserResponse> getAllUsers(
            @RequestHeader("X-User-Role") String role
    ) {
        if (!role.equals("ADMIN")) {
            throw new RuntimeException("Access Denied");
        }

        return userService.getAllUsers();
    }

    // Logged in user
    @GetMapping("/users/me")
    public UserResponse getCurrentUser(
            @RequestHeader("X-User-Id") String userId
    ) {
        return userService.getUserById(UUID.fromString(userId));
    }

}

