package com.avik.microservices.catalogue_service.controller;

import com.avik.microservices.catalogue_service.dto.ProductRequestDto;
import com.avik.microservices.catalogue_service.dto.ProductResponseDto;
import com.avik.microservices.catalogue_service.entity.Product;
import com.avik.microservices.catalogue_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/add")
    public ProductResponseDto addProduct(@Valid @RequestBody ProductRequestDto productRequestDto) {
        return productService.create(productRequestDto);
    }

    @PutMapping("/update/{id}")
    public ProductResponseDto updateProduct(@PathVariable UUID id, @Valid @RequestBody ProductRequestDto productRequestDto) {
        return productService.update(id, productRequestDto);
    }

    @GetMapping("/{id}")
    public ProductResponseDto getProductById(@PathVariable UUID id) {
        return productService.findById(id);
    }

    @GetMapping("/all")
    public List<ProductResponseDto> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/category/{categoryId}")
    public List<ProductResponseDto> getByCategory(
            @PathVariable UUID categoryId) {
        return productService.findByCategory(categoryId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        productService.deleteById(id);
    }

    @GetMapping("/sku/{sku}/exists")
    public Boolean skuExists(@PathVariable String sku) {
        return productService.existsBySku(sku);
    }

    @GetMapping("/sku/{sku}")
    public ProductResponseDto getBySku(@PathVariable String sku) {
        return productService.findBySku(sku);
    }

}
