package com.avik.microservices.inventory_service.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record InventoryResponseDto(
        UUID id,
        String sku,
        Integer availableStock,
        Integer reservedStock,
        LocalDateTime lastUpdated
) {
}
