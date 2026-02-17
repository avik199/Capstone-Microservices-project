package com.avik.microservices.inventory_service.service;

import com.avik.microservices.inventory_service.dto.InventoryRequestDto;
import com.avik.microservices.inventory_service.dto.InventoryResponseDto;

import java.util.List;

public interface InventoryService {
    InventoryResponseDto create(InventoryRequestDto inventoryRequestDto);
    InventoryResponseDto update(String sku,InventoryRequestDto inventoryRequestDto);
    InventoryResponseDto getBySku(String sku);
    List<InventoryResponseDto> getAll();
    void delete(String sku);
    void reserveStock(String sku, Integer quantity);
    void releaseStock(String sku, Integer quantity);

}
