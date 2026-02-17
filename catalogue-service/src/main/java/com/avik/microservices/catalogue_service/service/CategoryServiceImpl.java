package com.avik.microservices.catalogue_service.service;

import com.avik.microservices.catalogue_service.dto.CategoryRequestDto;
import com.avik.microservices.catalogue_service.dto.CategoryResponseDto;
import com.avik.microservices.catalogue_service.entity.Category;
import com.avik.microservices.catalogue_service.exception.ResourceNotFoundException;
import com.avik.microservices.catalogue_service.mapper.CategoryMapper;
import com.avik.microservices.catalogue_service.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {


    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;

    @Override
    public CategoryResponseDto create(CategoryRequestDto categoryRequestDto) {
        return mapper.toDTO(categoryRepository.save(mapper.toEntity(categoryRequestDto)));
    }

    @Override
    public CategoryResponseDto update(UUID id, CategoryRequestDto categoryRequestDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        category.setName(categoryRequestDto.name());
        return mapper.toDTO(categoryRepository.save(category));
    }

    @Override
    public CategoryResponseDto findById(UUID id) {
        return categoryRepository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    @Override
    public List<CategoryResponseDto> findAll() {
        return mapper.toDtoList(categoryRepository.findAll());
    }

    @Override
    public void deleteById(UUID id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found");
        }
        categoryRepository.deleteById(id);
    }
}
