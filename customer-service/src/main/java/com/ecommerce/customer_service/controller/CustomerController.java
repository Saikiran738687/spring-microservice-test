package com.ecommerce.customer_service.controller;

import com.ecommerce.customer_service.dto.CustomerDto;
import com.ecommerce.customer_service.service.CustomerService;
import com.ecommerce.customer_service.util.ResponseStructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // ================= SAVE =================
    @PostMapping(
            consumes = { "application/json", "application/xml" },
            produces = { "application/json", "application/xml" }
    )
    public ResponseEntity<ResponseStructure<CustomerDto>> saveCustomer(
            @RequestBody CustomerDto customerDto) {

        CustomerDto savedCustomer = customerService.saveCustomer(customerDto);

        ResponseStructure<CustomerDto> structure = new ResponseStructure<>();
        structure.setStatus(HttpStatus.CREATED.value());
        structure.setMessage("Customer Created Successfully");
        structure.setData(savedCustomer);

        return new ResponseEntity<>(structure, HttpStatus.CREATED);
    }

    // ================= GET ALL =================
    @GetMapping(produces = { "application/json", "application/xml" })
    public ResponseEntity<ResponseStructure<List<CustomerDto>>> getAllCustomers() {

        List<CustomerDto> customers = customerService.getAllCustomers();

        ResponseStructure<List<CustomerDto>> structure = new ResponseStructure<>();
        structure.setStatus(HttpStatus.OK.value());
        structure.setMessage("Customers Fetched Successfully");
        structure.setData(customers);

        return new ResponseEntity<>(structure, HttpStatus.OK);
    }

    // ================= GET BY ID =================
    @GetMapping(value = "/{id}", produces = { "application/json", "application/xml" })
    public ResponseEntity<ResponseStructure<CustomerDto>> getCustomerById(
            @PathVariable Long id) {

        CustomerDto customer = customerService.getCustomerById(id);

        ResponseStructure<CustomerDto> structure = new ResponseStructure<>();
        structure.setStatus(HttpStatus.OK.value());
        structure.setMessage("Customer Found");
        structure.setData(customer);

        return new ResponseEntity<>(structure, HttpStatus.OK);
    }

    // ================= DELETE =================
    @DeleteMapping(value = "/{id}", produces = { "application/json", "application/xml" })
    public ResponseEntity<ResponseStructure<CustomerDto>> deleteCustomer(
            @PathVariable Long id) {

        CustomerDto customer = customerService.deleteCustomer(id);

        ResponseStructure<CustomerDto> structure = new ResponseStructure<>();
        structure.setStatus(HttpStatus.OK.value());
        structure.setMessage("Customer Deleted Successfully");
        structure.setData(customer);

        return new ResponseEntity<>(structure, HttpStatus.OK);
    }

    // ================= UPDATE =================
    @PutMapping(
            value = "/{id}",
            consumes = { "application/json", "application/xml" },
            produces = { "application/json", "application/xml" }
    )
    public ResponseEntity<ResponseStructure<CustomerDto>> updateCustomer(
            @PathVariable Long id,
            @RequestBody CustomerDto customerDto) {

        CustomerDto updatedCustomer = customerService.updateCustomer(id, customerDto);

        ResponseStructure<CustomerDto> structure = new ResponseStructure<>();
        structure.setStatus(HttpStatus.OK.value());
        structure.setMessage("Customer Updated Successfully");
        structure.setData(updatedCustomer);

        return new ResponseEntity<>(structure, HttpStatus.OK);
    }
}