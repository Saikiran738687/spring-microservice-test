package com.ecommerce.product_service.dto;

import lombok.Data;
import java.util.List;

@Data
public class ProductResponseDTO {

    private Long id;

    private String name;
    private Double price;

    // ✅ ADDED
    private Integer stock;

    private Long categoryId;
    private String categoryName;

    private Long brandId;
    private String brandName;

    private List<SupplierDTO> suppliers;
}