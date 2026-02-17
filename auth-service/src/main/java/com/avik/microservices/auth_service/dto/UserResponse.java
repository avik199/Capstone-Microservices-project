package com.avik.microservices.auth_service.dto;

import com.avik.microservices.auth_service.Role;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String name,
        String email,
        String address,
        String phone,
        Role role
) {}

