package com.ecommerce.customer_service.serviceImpl;


import com.ecommerce.customer_service.dto.CustomerDto;
import com.ecommerce.customer_service.dto.AddressDto;
import com.ecommerce.customer_service.entity.Customer;
import com.ecommerce.customer_service.entity.Address;
import com.ecommerce.customer_service.repository.CustomerRepository;
import com.ecommerce.customer_service.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // ================= DTO → ENTITY =================
    private Customer convertToEntity(CustomerDto dto) {

        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());

        if (dto.getAddresses() != null) {
            List<Address> addresses = dto.getAddresses().stream().map(addressDto -> {
                Address address = new Address();
                address.setId(addressDto.getId());
                address.setStreet(addressDto.getStreet());
                address.setCity(addressDto.getCity());
                address.setState(addressDto.getState());
                address.setZipCode(addressDto.getZipCode());
                address.setCustomer(customer); // IMPORTANT
                return address;
            }).collect(Collectors.toList());

            customer.setAddresses(addresses);
        }

        return customer;
    }

    // ================= ENTITY → DTO =================
    private CustomerDto convertToDto(Customer customer) {

        CustomerDto dto = new CustomerDto();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());

        if (customer.getAddresses() != null) {
            List<AddressDto> addressDtos = customer.getAddresses().stream().map(address -> {
                AddressDto adto = new AddressDto();
                adto.setId(address.getId());
                adto.setStreet(address.getStreet());
                adto.setCity(address.getCity());
                adto.setState(address.getState());
                adto.setZipCode(address.getZipCode());
                return adto;
            }).collect(Collectors.toList());

            dto.setAddresses(addressDtos);
        }

        return dto;
    }

    // ================= SAVE =================
    @Override
    public CustomerDto saveCustomer(CustomerDto customerDto) {

        Customer customer = convertToEntity(customerDto);
        Customer savedCustomer = customerRepository.save(customer);

        return convertToDto(savedCustomer);
    }

    // ================= GET ALL =================
    @Override
    public List<CustomerDto> getAllCustomers() {

        List<Customer> customers = customerRepository.findAll();

        return customers.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // ================= GET BY ID =================
    @Override
    public CustomerDto getCustomerById(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer Not Found"));

        return convertToDto(customer);
    }

    // ================= DELETE =================
    @Override
    public CustomerDto deleteCustomer(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer Not Found"));

        customerRepository.delete(customer);

        return convertToDto(customer);
    }
    
    @Override
    public CustomerDto updateCustomer(Long id, CustomerDto customerDto) {

        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer Not Found"));

        existingCustomer.setName(customerDto.getName());
        existingCustomer.setEmail(customerDto.getEmail());
        existingCustomer.setPhone(customerDto.getPhone());

        if (customerDto.getAddresses() != null) {
            List<Address> addresses = customerDto.getAddresses().stream().map(addressDto -> {
                Address address = new Address();
                address.setId(addressDto.getId());
                address.setStreet(addressDto.getStreet());
                address.setCity(addressDto.getCity());
                address.setState(addressDto.getState());
                address.setZipCode(addressDto.getZipCode());
                address.setCustomer(existingCustomer);
                return address;
            }).collect(Collectors.toList());

            existingCustomer.setAddresses(addresses);
        }

        Customer updatedCustomer = customerRepository.save(existingCustomer);

        return convertToDto(updatedCustomer);
    }
}
