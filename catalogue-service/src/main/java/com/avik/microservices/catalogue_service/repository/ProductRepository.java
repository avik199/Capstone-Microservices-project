package com.avik.microservices.catalogue_service.repository;

import com.avik.microservices.catalogue_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByCategoryId(UUID categoryId);

    Boolean existsBySku(String sku);
    Optional<Product> findBySku(String sku);
}
