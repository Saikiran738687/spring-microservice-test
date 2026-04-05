package com.ecommerce.order_service.dto;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "payment")
public class PaymentDto {

    private Long id;
    private String paymentMethod;
    private String paymentStatus;
    private Double amount;
}
