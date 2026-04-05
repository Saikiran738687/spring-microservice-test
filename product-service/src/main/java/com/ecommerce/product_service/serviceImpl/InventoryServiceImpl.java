package com.ecommerce.product_service.serviceImpl;


import com.ecommerce.product_service.dto.*;
import com.ecommerce.product_service.entity.*;
import com.ecommerce.product_service.repository.*;
import com.ecommerce.product_service.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public InventoryResponseDTO createOrUpdate(InventoryRequestDTO dto) {

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Inventory inventory = inventoryRepository.findByProduct_Id(dto.getProductId())
                .orElse(new Inventory());

        inventory.setProduct(product);
        inventory.setQuantity(dto.getQuantity());
        inventory.setLocation(dto.getLocation());

        Inventory saved = inventoryRepository.save(inventory);

        return mapToDTO(saved);
    }

    @Override
    public InventoryResponseDTO getByProductId(Long productId) {

        Inventory inventory = inventoryRepository.findByProduct_Id(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

        return mapToDTO(inventory);
    }

    // 🔁 MAPPER
    private InventoryResponseDTO mapToDTO(Inventory inventory) {

        InventoryResponseDTO dto = new InventoryResponseDTO();
        dto.setId(inventory.getId());
        dto.setProductId(inventory.getProduct().getId());
        dto.setQuantity(inventory.getQuantity());
        dto.setLocation(inventory.getLocation());

        return dto;
    }
}
