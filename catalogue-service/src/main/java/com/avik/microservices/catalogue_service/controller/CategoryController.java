package com.avik.microservices.catalogue_service.controller;

import com.avik.microservices.catalogue_service.dto.CategoryRequestDto;
import com.avik.microservices.catalogue_service.dto.CategoryResponseDto;
import com.avik.microservices.catalogue_service.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/add")
    public CategoryResponseDto addCategory(@Valid @RequestBody CategoryRequestDto categoryRequestDto) {
        return categoryService.create(categoryRequestDto);
    }

    @PutMapping("/update/{id}")
    public CategoryResponseDto updateCategory(@PathVariable UUID id,@Valid @RequestBody CategoryRequestDto categoryRequestDto) {
        return categoryService.update(id, categoryRequestDto);
    }

    @GetMapping("/{id}")
    public CategoryResponseDto getCategoryById(@PathVariable UUID id) {
        return categoryService.findById(id);
    }

    @GetMapping("/all")
    public java.util.List<CategoryResponseDto> getAllCategories() {
        return categoryService.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteCategoryById(@PathVariable UUID id) {
        categoryService.deleteById(id);
    }
}
