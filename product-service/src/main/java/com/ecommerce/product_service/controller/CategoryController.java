package com.ecommerce.product_service.controller;

import com.ecommerce.product_service.dto.*;
import com.ecommerce.product_service.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public CategoryResponseDTO create(@RequestBody CategoryRequestDTO dto) {
        return categoryService.createCategory(dto);
    }

    @GetMapping("/{id}")
    public CategoryResponseDTO getById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping
    public List<CategoryResponseDTO> getAll() {
        return categoryService.getAllCategories();
    }

    @PutMapping("/{id}")
    public CategoryResponseDTO update(
            @PathVariable Long id,
            @RequestBody CategoryRequestDTO dto) {
        return categoryService.updateCategory(id, dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        return categoryService.deleteCategory(id);
    }
}