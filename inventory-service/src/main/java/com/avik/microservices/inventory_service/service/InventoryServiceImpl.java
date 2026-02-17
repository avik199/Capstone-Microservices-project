package com.avik.microservices.inventory_service.service;

import com.avik.microservices.inventory_service.client.CatalogueClient;
import com.avik.microservices.inventory_service.dto.InventoryRequestDto;
import com.avik.microservices.inventory_service.dto.InventoryResponseDto;
import com.avik.microservices.inventory_service.entity.Inventory;
import com.avik.microservices.inventory_service.exception.BadRequestException;
import com.avik.microservices.inventory_service.exception.ResourceNotFoundException;
import com.avik.microservices.inventory_service.mapper.InventoryMapper;
import com.avik.microservices.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final CatalogueClient catalogueClient;
    private final InventoryMapper inventoryMapper;

    @Override
    public InventoryResponseDto create(InventoryRequestDto inventoryRequestDto) {
        if(!catalogueClient.existsBySku(inventoryRequestDto.sku()))
            throw new BadRequestException("Invalid sku");
        if (inventoryRepository.existsBySku(inventoryRequestDto.sku()))
            throw new BadRequestException("Inventory with the given sku already exists");

        Inventory inventory = inventoryMapper.toEntity(inventoryRequestDto);
        inventory.setLastUpdated(LocalDateTime.now());
        return inventoryMapper.toDto(inventoryRepository.save(inventory));
    }

    @Override
    public InventoryResponseDto update(String sku, InventoryRequestDto inventoryRequestDto) {
        Inventory inventory = inventoryRepository.findBySku(sku)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory with the given sku not found"));
        inventory.setLastUpdated(LocalDateTime.now());
        inventory.setAvailableStock(inventoryRequestDto.availableStock());
        inventory.setReservedStock(inventoryRequestDto.reservedStock());
        return inventoryMapper.toDto(inventoryRepository.save(inventory));
    }

    @Override
    public InventoryResponseDto getBySku(String sku) {
        Inventory inventory = inventoryRepository.findBySku(sku)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory with the given sku not found"));
        return inventoryMapper.toDto(inventory);
    }

    @Override
    public List<InventoryResponseDto> getAll() {
        return inventoryRepository.findAll()
                .stream()
                .map(inventoryMapper::toDto)
                .toList();
    }

    @Override
    public void delete(String sku) {
        Inventory inventory = inventoryRepository.findBySku(sku)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory with the given id not found"));
        inventoryRepository.delete(inventory);

    }

    @Override
    public void reserveStock(String sku, Integer quantity) {

        Inventory inventory = inventoryRepository.findBySku(sku)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Inventory not found"));

        if (inventory.getAvailableStock() < quantity) {
            throw new BadRequestException("Insufficient stock");
        }

        inventory.setAvailableStock(
                inventory.getAvailableStock() - quantity
        );

        inventory.setReservedStock(
                inventory.getReservedStock() + quantity
        );

        inventory.setLastUpdated(LocalDateTime.now());

        // No save() needed because of @Transactional
    }

    @Override
    public void releaseStock(String sku, Integer quantity) {

        Inventory inventory = inventoryRepository.findBySku(sku)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));

        inventory.setReservedStock(inventory.getReservedStock() - quantity);
        inventory.setAvailableStock(inventory.getAvailableStock() + quantity);
    }


}
