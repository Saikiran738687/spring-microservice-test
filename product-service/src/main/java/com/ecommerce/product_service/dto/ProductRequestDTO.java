package com.ecommerce.product_service.dto;

import lombok.Data;
import java.util.List;

@Data
public class ProductRequestDTO {

    private String name;
    private Double price;

    // ✅ ADDED
    private Integer stock;

    private Long categoryId;
    private Long brandId;

    private List<Long> supplierIds;
}