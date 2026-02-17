package com.avik.microservices.catalogue_service.service;

import com.avik.microservices.catalogue_service.dto.ProductRequestDto;
import com.avik.microservices.catalogue_service.dto.ProductResponseDto;
import com.avik.microservices.catalogue_service.entity.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductResponseDto create(ProductRequestDto productRequestDto);
    ProductResponseDto update(UUID id, ProductRequestDto productRequestDto);
    ProductResponseDto findById(UUID id);
    List<ProductResponseDto> findAll();
    List<ProductResponseDto> findByCategory(UUID categoryId);
    void deleteById(UUID id);

    Boolean existsBySku(String sku);
    ProductResponseDto findBySku(String sku);
}
