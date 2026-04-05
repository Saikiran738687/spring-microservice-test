package com.ecommerce.product_service.service;


import com.ecommerce.product_service.dto.InventoryRequestDTO;
import com.ecommerce.product_service.dto.InventoryResponseDTO;

public interface InventoryService {

    InventoryResponseDTO createOrUpdate(InventoryRequestDTO dto);

    InventoryResponseDTO getByProductId(Long productId);
}
