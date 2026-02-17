package com.avik.microservices.order_service.client;

import com.avik.microservices.order_service.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "catalogue-service")
public interface CatalogueClient {

    @GetMapping("/api/products/sku/{sku}")
    ProductResponse getProduct(@PathVariable String sku);
}

