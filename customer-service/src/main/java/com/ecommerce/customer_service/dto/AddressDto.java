package com.ecommerce.customer_service.dto;


import lombok.Data;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "address")
public class AddressDto {

    private Long id;
    private String street;
    private String city;
    private String state;
    private String zipCode;
}
