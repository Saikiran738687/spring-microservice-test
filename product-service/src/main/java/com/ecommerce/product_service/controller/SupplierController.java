package com.ecommerce.product_service.controller;


import com.ecommerce.product_service.dto.*;
import com.ecommerce.product_service.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping
    public SupplierResponseDTO create(@RequestBody SupplierRequestDTO dto) {
        return supplierService.createSupplier(dto);
    }

    @GetMapping("/{id}")
    public SupplierResponseDTO getById(@PathVariable Long id) {
        return supplierService.getSupplierById(id);
    }

    @GetMapping
    public List<SupplierResponseDTO> getAll() {
        return supplierService.getAllSuppliers();
    }

    @PutMapping("/{id}")
    public SupplierResponseDTO update(
            @PathVariable Long id,
            @RequestBody SupplierRequestDTO dto) {
        return supplierService.updateSupplier(id, dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        return supplierService.deleteSupplier(id);
    }
}
