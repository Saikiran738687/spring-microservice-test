package com.ecommerce.product_service.serviceImpl;


import com.ecommerce.product_service.dto.CategoryRequestDTO;
import com.ecommerce.product_service.dto.CategoryResponseDTO;
import com.ecommerce.product_service.dto.CategoryWithProductsDTO;
import com.ecommerce.product_service.entity.Category;
import com.ecommerce.product_service.repository.CategoryRepository;
import com.ecommerce.product_service.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO requestDTO) {

        Category category = new Category();
        category.setName(requestDTO.getName());
        category.setDescription(requestDTO.getDescription());

        Category saved = categoryRepository.save(category);

        return mapToDTO(saved);
    }

    @Override
    public CategoryResponseDTO getCategoryById(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        return mapToDTO(category);
    }

    @Override
    public List<CategoryResponseDTO> getAllCategories() {

        return categoryRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO requestDTO) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(requestDTO.getName());
        category.setDescription(requestDTO.getDescription());

        return mapToDTO(categoryRepository.save(category));
    }

    @Override
    public String deleteCategory(Long id) {

        categoryRepository.deleteById(id);
        return "Category deleted";
    }

    @Override
    public CategoryWithProductsDTO getCategoryWithProducts(Long categoryId) {
        return null; // we can implement later
    }

    private CategoryResponseDTO mapToDTO(Category category) {

        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());

        return dto;
    }
}