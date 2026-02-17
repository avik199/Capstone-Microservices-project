package com.avik.microservices.catalogue_service.service;

import com.avik.microservices.catalogue_service.dto.ProductRequestDto;
import com.avik.microservices.catalogue_service.dto.ProductResponseDto;
import com.avik.microservices.catalogue_service.entity.Category;
import com.avik.microservices.catalogue_service.entity.Product;
import com.avik.microservices.catalogue_service.exception.ResourceNotFoundException;
import com.avik.microservices.catalogue_service.mapper.ProductMapper;
import com.avik.microservices.catalogue_service.repository.CategoryRepository;
import com.avik.microservices.catalogue_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponseDto create(ProductRequestDto productRequestDto) {
        Category category = categoryRepository.findById(productRequestDto.categoryId()).orElseThrow(()->
                new ResourceNotFoundException("Category not found"));
        Product product = productMapper.toEntity(productRequestDto);
        product.setCategory(category);
        product.setCreatedDate(LocalDateTime.now());
        product.setUpdatedDate(LocalDateTime.now());

        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    public ProductResponseDto update(UUID id, ProductRequestDto productRequestDto) {
        Product product = productRepository.findById(id).orElseThrow(()->
        new ResourceNotFoundException("Product not found"));

        Category category = categoryRepository.findById(productRequestDto.categoryId()).orElseThrow(()->
                new ResourceNotFoundException("Category not found"));

        product.setName(productRequestDto.name());
        product.setDescription(productRequestDto.description());
        product.setCategory(category);
        product.setBrand(productRequestDto.brand());
        product.setSku(productRequestDto.sku());
        product.setUpdatedDate(LocalDateTime.now());

        return productMapper.toDto(product);//@transactional takes care of saving and auto commit
    }

    @Override
    public ProductResponseDto findById(UUID id) {
        return productRepository.findById(id).map(productMapper::toDto).orElseThrow(()->
                new ResourceNotFoundException("Product not found"));
    }

    @Override
    public List<ProductResponseDto> findAll() {
        return productMapper.toDtoList(productRepository.findAll());
    }

    @Override
    public List<ProductResponseDto> findByCategory(UUID categoryId) {
        return productMapper.toDtoList(productRepository.findByCategoryId(categoryId));
    }

    @Override
    public void deleteById(UUID id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found");
        }
        productRepository.deleteById(id);
    }

    @Override
    public Boolean existsBySku(String sku) {
//        System.out.println("Checking sku: " + sku);
        return productRepository.existsBySku(sku);
    }

    @Override
    public ProductResponseDto findBySku(String sku) {
        return productRepository.findBySku(sku)
                .map(productMapper::toDto)
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));
    }
}
