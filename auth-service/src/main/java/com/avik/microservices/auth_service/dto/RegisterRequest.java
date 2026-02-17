package com.avik.microservices.auth_service.dto;

import com.avik.microservices.auth_service.Role;

public record RegisterRequest(
        String name,
        String email,
        String password,
        String address,
        String phone,
        Role role
) {}
