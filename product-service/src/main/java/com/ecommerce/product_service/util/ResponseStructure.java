package com.ecommerce.product_service.util;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ResponseStructure<T> {

    private String message;
    private int status;
    private LocalDateTime timestamp = LocalDateTime.now();
    private T data;
}
