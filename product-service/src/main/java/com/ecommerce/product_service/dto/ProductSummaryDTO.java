package com.ecommerce.product_service.dto;

import lombok.Data;

@Data
public class ProductSummaryDTO {

    private Long id;
    private String name;
    private Double price;
}