package com.avik.microservices.catalogue_service.dto;

import java.time.LocalDate;
import java.util.UUID;

public record ProductResponseDto(
        UUID id,
        String name,
        String description,
        String brand,
        String sku,
        LocalDate createdDate,
        LocalDate updatedDate,
        UUID categoryId
) {}
