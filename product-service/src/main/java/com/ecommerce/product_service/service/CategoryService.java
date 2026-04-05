package com.ecommerce.product_service.service;

import com.ecommerce.product_service.dto.CategoryRequestDTO;
import com.ecommerce.product_service.dto.CategoryResponseDTO;
import com.ecommerce.product_service.dto.CategoryWithProductsDTO;

import java.util.List;

public interface CategoryService {

    CategoryResponseDTO createCategory(CategoryRequestDTO requestDTO);

    CategoryResponseDTO getCategoryById(Long id);

    List<CategoryResponseDTO> getAllCategories();

    CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO requestDTO);

    String deleteCategory(Long id);

    CategoryWithProductsDTO getCategoryWithProducts(Long categoryId);
}