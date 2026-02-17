package com.avik.microservices.catalogue_service.mapper;

import com.avik.microservices.catalogue_service.dto.CategoryRequestDto;
import com.avik.microservices.catalogue_service.dto.CategoryResponseDto;
import com.avik.microservices.catalogue_service.entity.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toEntity(CategoryRequestDto categoryRequestDto);

    CategoryResponseDto toDTO(Category category);

    List<CategoryResponseDto> toDtoList(List<Category> categories);
}
