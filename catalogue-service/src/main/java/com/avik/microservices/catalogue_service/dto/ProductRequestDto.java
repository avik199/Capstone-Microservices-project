package com.avik.microservices.catalogue_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ProductRequestDto(
        @NotBlank String name,
        String description,
        String brand,
        @NotBlank String sku,
        @NotNull UUID categoryId
) {}
