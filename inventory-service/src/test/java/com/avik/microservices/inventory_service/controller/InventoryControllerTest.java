package com.avik.microservices.inventory_service.controller;

import com.avik.microservices.inventory_service.dto.InventoryResponseDto;
import com.avik.microservices.inventory_service.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class InventoryControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockitoBean
    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    void shouldReturnInventoryBySku() throws Exception {

        InventoryResponseDto response = new InventoryResponseDto(
                UUID.randomUUID(),
                "SKU123",
                10,
                0,
                LocalDateTime.now()
        );

        Mockito.when(inventoryService.getBySku("SKU123"))
                .thenReturn(response);

        mockMvc.perform(get("/api/inventory/SKU123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sku").value("SKU123"));
    }
}