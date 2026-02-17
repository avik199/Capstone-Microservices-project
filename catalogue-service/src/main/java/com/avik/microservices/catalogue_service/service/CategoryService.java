package com.avik.microservices.catalogue_service.service;

import com.avik.microservices.catalogue_service.dto.CategoryRequestDto;
import com.avik.microservices.catalogue_service.dto.CategoryResponseDto;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryResponseDto create(CategoryRequestDto categoryRequestDto);
    CategoryResponseDto update(UUID id,CategoryRequestDto categoryRequestDto);
    CategoryResponseDto findById(UUID id);
    List<CategoryResponseDto> findAll();
    void deleteById(UUID id);
}
