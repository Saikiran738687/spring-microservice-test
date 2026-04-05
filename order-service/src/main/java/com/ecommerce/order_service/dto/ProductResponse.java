package com.ecommerce.order_service.dto;

import lombok.Data;

@Data
public class ProductResponse {

    private Long id;
    private String name;
    private double price;
}