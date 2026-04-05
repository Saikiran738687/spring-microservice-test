package com.ecommerce.order_service.dto;


import lombok.Data;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "orderItem")
public class OrderItemDto {

    private Long id;
    private Long productId;
    private Integer quantity;
    private Double price;
    private Double subTotal;
}