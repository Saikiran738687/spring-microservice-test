package com.ecommerce.order_service.util;

import java.time.LocalDateTime;


import lombok.Data;

@Data
public class ResponseStructure<T> {

    private int status;
    private String message;
    private T data;
    private LocalDateTime timeStamp = LocalDateTime.now();
}
