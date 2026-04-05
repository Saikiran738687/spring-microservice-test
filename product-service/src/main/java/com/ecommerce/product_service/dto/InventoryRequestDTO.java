package com.ecommerce.product_service.dto;


import lombok.Data;

@Data
public class InventoryRequestDTO {

    private Long productId;
    private Integer quantity;
    private String location;
}
