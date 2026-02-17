package com.avik.microservices.inventory_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "catalogue-service")
public interface CatalogueClient {
    @GetMapping("/api/products/sku/{sku}/exists")
    Boolean existsBySku(@PathVariable("sku") String sku);
}
