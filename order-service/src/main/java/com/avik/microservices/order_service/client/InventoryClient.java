package com.avik.microservices.order_service.client;

import com.avik.microservices.order_service.dto.InventoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory-service")
public interface InventoryClient {

    @GetMapping("/api/inventory/{sku}")
    InventoryResponse checkStock(@PathVariable String sku);

    @PostMapping("/api/inventory/reserve/{sku}")
    void reserveStock(@PathVariable String sku, @RequestParam Integer quantity);

    @PostMapping("/api/inventory/release/{sku}")
    void releaseStock(@PathVariable String sku, @RequestParam Integer quantity);
}

