package com.avik.microservices.catalogue_service.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record CategoryResponseDto(
        UUID id,
        String name
) {
}
