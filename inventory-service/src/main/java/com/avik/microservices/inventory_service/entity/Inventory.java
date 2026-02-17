package com.avik.microservices.inventory_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "inventories")
public class Inventory {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "sku", unique = true, nullable = false)
    private String sku;

    @Column(name = "available_stock", nullable = false)
    private Integer availableStock;

    @Column(name = "reserved_stock", nullable = false)
    private Integer reservedStock;

    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;
}
