package com.avik.microservices.auth_service.dto;

public record LoginRequest(
        String email,
        String password
) {}

