package com.avik.microservices.inventory_service.service;

import com.avik.microservices.inventory_service.client.CatalogueClient;
import com.avik.microservices.inventory_service.entity.Inventory;
import com.avik.microservices.inventory_service.exception.BadRequestException;
import com.avik.microservices.inventory_service.mapper.InventoryMapper;
import com.avik.microservices.inventory_service.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryServiceImplTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private CatalogueClient catalogueClient;

    @Mock
    private InventoryMapper inventoryMapper;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    private Inventory inventory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        inventory = Inventory.builder()
                .id(UUID.randomUUID())
                .sku("SKU123")
                .availableStock(10)
                .reservedStock(0)
                .lastUpdated(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldReserveStockSuccessfully() {

        when(inventoryRepository.findBySku("SKU123"))
                .thenReturn(Optional.of(inventory));

        inventoryService.reserveStock("SKU123", 5);

        assertEquals(5, inventory.getAvailableStock());
        assertEquals(5, inventory.getReservedStock());
    }

    @Test
    void shouldThrowExceptionIfInsufficientStock() {

        when(inventoryRepository.findBySku("SKU123"))
                .thenReturn(Optional.of(inventory));

        assertThrows(BadRequestException.class,
                () -> inventoryService.reserveStock("SKU123", 20));
    }
}

