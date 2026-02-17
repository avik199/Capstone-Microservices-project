package com.avik.microservices.inventory_service.repository;

import com.avik.microservices.inventory_service.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface InventoryRepository extends JpaRepository<Inventory, UUID> {
    Boolean existsBySku(String sku);
    Optional<Inventory> findBySku(String sku);
}
