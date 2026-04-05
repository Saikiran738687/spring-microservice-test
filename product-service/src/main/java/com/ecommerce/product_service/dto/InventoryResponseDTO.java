package com.ecommerce.product_service.dto;


import lombok.Data;

@Data
public class InventoryResponseDTO {

    private Long id;
    private Long productId;
    private Integer quantity;
    private String location;
}
