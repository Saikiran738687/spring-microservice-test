package com.ecommerce.product_service.dto;


import lombok.Data;
import java.util.List;

@Data
public class SupplierWithProductsDTO {

    private Long id;
    private String name;

    private List<ProductSummaryDTO> products;
}
