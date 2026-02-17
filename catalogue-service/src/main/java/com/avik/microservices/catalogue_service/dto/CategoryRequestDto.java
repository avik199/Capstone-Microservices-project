package com.avik.microservices.catalogue_service.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequestDto(
        @NotBlank String name
) {
}
