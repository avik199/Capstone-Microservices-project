package com.avik.microservices.order_service.dto;

import lombok.Data;

@Data
public class InventoryResponse {
    private String sku;
    private Integer availableStock;
    private Integer reservedStock;
}

