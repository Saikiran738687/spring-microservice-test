package com.ecommerce.product_service.controller;

import com.ecommerce.product_service.dto.*;
import com.ecommerce.product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    
    @GetMapping("/test")
    public String test() {
        return "Product Service Working";
    }

    // CREATE PRODUCT
    @PostMapping
    public ProductResponseDTO createProduct(@RequestBody ProductRequestDTO requestDTO) {
        return productService.createProduct(requestDTO);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ProductResponseDTO getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    // GET ALL
    @GetMapping
    public List<ProductResponseDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    // UPDATE
    @PutMapping("/{id}")
    public ProductResponseDTO updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequestDTO requestDTO) {
        return productService.updateProduct(id, requestDTO);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

    // SEARCH BY CATEGORY
    @GetMapping("/category/{categoryId}")
    public List<ProductSummaryDTO> getByCategory(@PathVariable Long categoryId) {
        return productService.getProductsByCategory(categoryId);
    }

    // SEARCH BY BRAND
    @GetMapping("/brand/{brandId}")
    public List<ProductSummaryDTO> getByBrand(@PathVariable Long brandId) {
        return productService.getProductsByBrand(brandId);
    }

    // SEARCH BY KEYWORD
    @GetMapping("/search")
    public List<ProductSummaryDTO> search(@RequestParam String keyword) {
        return productService.searchProducts(keyword);
    }
 // ✅ GET STOCK
    @GetMapping("/inventory/{productId}")
    public Integer getStock(@PathVariable Long productId) {
        return productService.getStock(productId);
    }

    // ✅ REDUCE STOCK
    @PostMapping("/inventory/reduce")
    public String reduceStock(
            @RequestParam Long productId,
            @RequestParam Integer quantity) {

        productService.reduceStock(productId, quantity);
        return "Stock reduced successfully";
    }
}