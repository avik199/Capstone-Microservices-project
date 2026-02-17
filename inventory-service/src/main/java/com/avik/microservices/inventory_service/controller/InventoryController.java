package com.avik.microservices.inventory_service.controller;

import com.avik.microservices.inventory_service.dto.InventoryRequestDto;
import com.avik.microservices.inventory_service.dto.InventoryResponseDto;
import com.avik.microservices.inventory_service.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/add")
    public InventoryResponseDto add(@Valid @RequestBody InventoryRequestDto inventoryRequestDto) {
        return inventoryService.create(inventoryRequestDto);
    }

    @PutMapping("/update/{sku}")
    public InventoryResponseDto update(@PathVariable String sku,@Valid @RequestBody InventoryRequestDto inventoryRequestDto) {
        return inventoryService.update(sku,inventoryRequestDto);
    }

    @GetMapping("/{sku}")
    public InventoryResponseDto getBySku(@PathVariable String sku) {
        return inventoryService.getBySku(sku);
    }

    @GetMapping("/all")
    public java.util.List<InventoryResponseDto> getAll() {
        return inventoryService.getAll();
    }

     @DeleteMapping("/{sku}")
    public void deleteBySku(@PathVariable String sku) {
         inventoryService.delete(sku);
    }

    @PostMapping("/reserve/{sku}")
    public void reserveStock(
            @PathVariable String sku,
            @RequestParam Integer quantity) {

        inventoryService.reserveStock(sku, quantity);
    }

    @PostMapping("/release/{sku}")
    public void releaseStock(
            @PathVariable String sku,
            @RequestParam Integer quantity) {

        inventoryService.releaseStock(sku, quantity);
    }

}

