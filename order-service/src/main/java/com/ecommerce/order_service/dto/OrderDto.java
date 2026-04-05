package com.ecommerce.order_service.dto;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JacksonXmlRootElement(localName = "order")
public class OrderDto {

    private Long id;
    private Long customerId;
    private LocalDateTime orderDate;
    private String status;
    private Double totalAmount;

    private List<OrderItemDto> orderItems;
    private PaymentDto payment;
}
