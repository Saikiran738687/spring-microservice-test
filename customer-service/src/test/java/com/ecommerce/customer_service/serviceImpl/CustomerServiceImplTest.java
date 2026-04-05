package com.ecommerce.customer_service.serviceImpl;


import com.ecommerce.customer_service.dto.CustomerDto;
import com.ecommerce.customer_service.entity.Customer;
import com.ecommerce.customer_service.repository.CustomerRepository;
import com.ecommerce.customer_service.serviceImpl.CustomerServiceImpl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    public CustomerServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    // ✅ SAVE CUSTOMER
    @Test
    void testSaveCustomer() {

        CustomerDto dto = new CustomerDto();
        dto.setName("John");
        dto.setEmail("john@test.com");
        dto.setPhone("1234567890");

        Customer saved = new Customer();
        saved.setId(1L);
        saved.setName("John");
        saved.setEmail("john@test.com");
        saved.setPhone("1234567890");

        when(customerRepository.save(any(Customer.class))).thenReturn(saved);

        CustomerDto result = customerService.saveCustomer(dto);

        assertNotNull(result);
        assertEquals("John", result.getName());
    }

    // ✅ GET ALL CUSTOMERS
    @Test
    void testGetAllCustomers() {

        Customer c1 = new Customer();
        c1.setId(1L);
        c1.setName("John");

        Customer c2 = new Customer();
        c2.setId(2L);
        c2.setName("Doe");

        when(customerRepository.findAll()).thenReturn(Arrays.asList(c1, c2));

        assertEquals(2, customerService.getAllCustomers().size());
    }

    // ✅ GET CUSTOMER BY ID
    @Test
    void testGetCustomerById() {

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        CustomerDto result = customerService.getCustomerById(1L);

        assertNotNull(result);
        assertEquals("John", result.getName());
    }

    // ❌ CUSTOMER NOT FOUND
    @Test
    void testGetCustomerById_NotFound() {

        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            customerService.getCustomerById(1L);
        });

        assertEquals("Customer Not Found", exception.getMessage());
    }

    // ✅ DELETE CUSTOMER
    @Test
    void testDeleteCustomer() {

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        doNothing().when(customerRepository).delete(customer);

        CustomerDto result = customerService.deleteCustomer(1L);

        assertEquals("John", result.getName());
        verify(customerRepository, times(1)).delete(customer);
    }

    // ❌ DELETE NOT FOUND
    @Test
    void testDeleteCustomer_NotFound() {

        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            customerService.deleteCustomer(1L);
        });

        assertEquals("Customer Not Found", exception.getMessage());
    }

    // ✅ UPDATE CUSTOMER
    @Test
    void testUpdateCustomer() {

        Customer existing = new Customer();
        existing.setId(1L);
        existing.setName("Old");

        CustomerDto dto = new CustomerDto();
        dto.setName("New");
        dto.setEmail("new@test.com");
        dto.setPhone("9999999999");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(customerRepository.save(any(Customer.class))).thenReturn(existing);

        CustomerDto result = customerService.updateCustomer(1L, dto);

        assertEquals("New", result.getName());
    }

    // ❌ UPDATE NOT FOUND
    @Test
    void testUpdateCustomer_NotFound() {

        CustomerDto dto = new CustomerDto();
        dto.setName("New");

        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            customerService.updateCustomer(1L, dto);
        });

        assertEquals("Customer Not Found", exception.getMessage());
    }
}
