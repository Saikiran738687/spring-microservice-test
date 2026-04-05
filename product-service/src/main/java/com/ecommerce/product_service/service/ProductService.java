package com.ecommerce.product_service.service;

import com.ecommerce.product_service.dto.ProductRequestDTO;
import com.ecommerce.product_service.dto.ProductResponseDTO;
import com.ecommerce.product_service.dto.ProductSummaryDTO;

import java.util.List;

public interface ProductService {

    ProductResponseDTO createProduct(ProductRequestDTO requestDTO);

    ProductResponseDTO getProductById(Long id);

    List<ProductResponseDTO> getAllProducts();

    ProductResponseDTO updateProduct(Long id, ProductRequestDTO requestDTO);

    String deleteProduct(Long id);

    List<ProductSummaryDTO> getProductsByCategory(Long categoryId);

    List<ProductSummaryDTO> getProductsByBrand(Long brandId);

    List<ProductSummaryDTO> searchProducts(String keyword);

    Integer getStock(Long productId);

    void reduceStock(Long productId, Integer quantity);
}