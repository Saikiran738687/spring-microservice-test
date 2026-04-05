package com.ecommerce.customer_service.util;


import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ResponseStructure<T> {

    private int status;
    private String message;
    private T data;
    private LocalDateTime timeStamp = LocalDateTime.now();
}
