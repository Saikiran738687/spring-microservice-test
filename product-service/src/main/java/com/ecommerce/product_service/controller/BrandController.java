package com.ecommerce.product_service.controller;


import com.ecommerce.product_service.dto.*;
import com.ecommerce.product_service.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @PostMapping
    public BrandResponseDTO create(@RequestBody BrandRequestDTO dto) {
        return brandService.createBrand(dto);
    }

    @GetMapping("/{id}")
    public BrandResponseDTO getById(@PathVariable Long id) {
        return brandService.getBrandById(id);
    }

    @GetMapping
    public List<BrandResponseDTO> getAll() {
        return brandService.getAllBrands();
    }

    @PutMapping("/{id}")
    public BrandResponseDTO update(
            @PathVariable Long id,
            @RequestBody BrandRequestDTO dto) {
        return brandService.updateBrand(id, dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        return brandService.deleteBrand(id);
    }
}
