package com.avik.microservices.catalogue_service.repository;

import com.avik.microservices.catalogue_service.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
