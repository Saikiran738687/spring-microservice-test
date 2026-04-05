package com.ecommerce.customer_service.service;


import com.ecommerce.customer_service.dto.CustomerDto;

import java.util.List;

public interface CustomerService {

    CustomerDto saveCustomer(CustomerDto customerDto);

    List<CustomerDto> getAllCustomers();

    CustomerDto getCustomerById(Long id);

    CustomerDto deleteCustomer(Long id);
    
    CustomerDto updateCustomer(Long id, CustomerDto customerDto);
}
