package com.avik.microservices.inventory_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InventoryRequestDto(
        @NotBlank String sku,
        @NotNull Integer availableStock,
        @NotNull Integer reservedStock


) {

}
