package com.avik.microservices.order_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponse {
    private String sku;
    private String name;
//    private BigDecimal price;
}
