package com.avik.microservices.order_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String sku;

    private Integer quantity;

//    private BigDecimal price;

    private UUID userId;      // NEW
    private String userEmail; // NEW

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
