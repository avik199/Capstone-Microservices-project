package com.avik.microservices.inventory_service.mapper;

import com.avik.microservices.inventory_service.dto.InventoryRequestDto;
import com.avik.microservices.inventory_service.dto.InventoryResponseDto;
import com.avik.microservices.inventory_service.entity.Inventory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    Inventory toEntity(InventoryRequestDto inventoryRequestDto);

    InventoryResponseDto toDto(Inventory inventory);

    List<InventoryResponseDto> toDtoList(List<Inventory> inventories);

}
