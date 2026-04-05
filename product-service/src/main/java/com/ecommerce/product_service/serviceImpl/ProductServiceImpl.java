package com.ecommerce.product_service.serviceImpl;

import com.ecommerce.product_service.dto.*;
import com.ecommerce.product_service.entity.*;
import com.ecommerce.product_service.repository.*;
import com.ecommerce.product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    // CREATE
    @Override
    @CacheEvict(value = {"productList","searchProducts","categoryProducts","brandProducts"}, allEntries = true)
    public ProductResponseDTO createProduct(ProductRequestDTO requestDTO) {

        Product product = new Product();
        product.setName(requestDTO.getName());
        product.setPrice(requestDTO.getPrice());
        product.setStock(requestDTO.getStock());

        product.setCategory(
                categoryRepository.findById(requestDTO.getCategoryId())
                        .orElseThrow()
        );

        product.setBrand(
                brandRepository.findById(requestDTO.getBrandId())
                        .orElseThrow()
        );

        product.setSuppliers(
                supplierRepository.findAllById(requestDTO.getSupplierIds())
        );

        return mapToDTO(productRepository.save(product));
    }

    // GET BY ID
    @Override
    @Cacheable(value = "products", key = "#id")
    public ProductResponseDTO getProductById(Long id) {
        return mapToDTO(productRepository.findById(id).orElseThrow());
    }

    // GET ALL
    @Override
    @Cacheable("productList")
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // UPDATE
    @Override
    @CacheEvict(value = "products", key = "#id")
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO requestDTO) {

        Product product = productRepository.findById(id).orElseThrow();

        product.setName(requestDTO.getName());
        product.setPrice(requestDTO.getPrice());
        product.setStock(requestDTO.getStock());

        return mapToDTO(productRepository.save(product));
    }

    // DELETE
    @Override
    @CacheEvict(value = "products", key = "#id")
    public String deleteProduct(Long id) {
        productRepository.deleteById(id);
        return "Deleted";
    }

    // CATEGORY
    @Override
    @Cacheable(value = "categoryProducts", key = "#categoryId")
    public List<ProductSummaryDTO> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategory_Id(categoryId)
                .stream()
                .map(this::mapToSummaryDTO)
                .toList();
    }

    // BRAND
    @Override
    @Cacheable(value = "brandProducts", key = "#brandId")
    public List<ProductSummaryDTO> getProductsByBrand(Long brandId) {
        return productRepository.findByBrand_Id(brandId)
                .stream()
                .map(this::mapToSummaryDTO)
                .toList();
    }

    // SEARCH
    @Override
    @Cacheable(value = "searchProducts", key = "#keyword")
    public List<ProductSummaryDTO> searchProducts(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(this::mapToSummaryDTO)
                .toList();
    }

    // STOCK
    @Override
    public Integer getStock(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow()
                .getStock();
    }

    @Override
    public void reduceStock(Long productId, Integer quantity) {

        Product product = productRepository.findById(productId)
                .orElseThrow();

        if (product.getStock() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }

        product.setStock(product.getStock() - quantity);
        productRepository.save(product);
    }

    // ================= FIXED MAPPER =================
    private ProductResponseDTO mapToDTO(Product product) {

        ProductResponseDTO dto = new ProductResponseDTO();

        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());

        // CATEGORY
        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getId());
            dto.setCategoryName(product.getCategory().getName());
        }

        // BRAND
        if (product.getBrand() != null) {
            dto.setBrandId(product.getBrand().getId());
            dto.setBrandName(product.getBrand().getName());
        }

        // SUPPLIERS
        if (product.getSuppliers() != null) {
            dto.setSuppliers(
                    product.getSuppliers()
                            .stream()
                            .map(s -> {
                                SupplierDTO sd = new SupplierDTO();
                                sd.setId(s.getId());
                                sd.setName(s.getName());
                                return sd;
                            })
                            .toList()
            );
        }

        return dto;
    }

    private ProductSummaryDTO mapToSummaryDTO(Product product) {

        ProductSummaryDTO dto = new ProductSummaryDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());

        return dto;
    }
}