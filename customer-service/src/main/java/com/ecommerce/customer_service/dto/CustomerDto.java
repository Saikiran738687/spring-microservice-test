package com.ecommerce.customer_service.dto;


import lombok.Data;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import java.util.List;

@Data
@JacksonXmlRootElement(localName = "customer")
public class CustomerDto {

    private Long id;
    private String name;
    private String email;
    private String phone;

    private List<AddressDto> addresses;
}
