package com.ecommerce.product_service.controller;


import com.ecommerce.product_service.dto.*;
import com.ecommerce.product_service.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    // CREATE or UPDATE
    @PostMapping
    public InventoryResponseDTO createOrUpdate(@RequestBody InventoryRequestDTO dto) {
        return inventoryService.createOrUpdate(dto);
    }

    // GET BY PRODUCT
    @GetMapping("/{productId}")
    public InventoryResponseDTO getByProduct(@PathVariable Long productId) {
        return inventoryService.getByProductId(productId);
    }
}