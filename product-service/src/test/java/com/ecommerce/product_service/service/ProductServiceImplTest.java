package com.ecommerce.product_service.service;

import com.ecommerce.product_service.dto.ProductResponseDTO;
import com.ecommerce.product_service.entity.Product;
import com.ecommerce.product_service.repository.ProductRepository;
import com.ecommerce.product_service.repository.CategoryRepository;
import com.ecommerce.product_service.repository.BrandRepository;
import com.ecommerce.product_service.repository.SupplierRepository;
import com.ecommerce.product_service.serviceImpl.ProductServiceImpl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    // ✅ NEW MOCKS (STEP 4.1)
    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    public ProductServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    // ✅ TEST: GET PRODUCT BY ID
    @Test
    void testGetProductById() {

        Product product = new Product();
        product.setId(1L);
        product.setName("Phone");
        product.setPrice(10000.0);
        product.setStock(10);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductResponseDTO result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals("Phone", result.getName());
        assertEquals(10000.0, result.getPrice());
    }

    // ✅ TEST: DELETE PRODUCT
    @Test
    void testDeleteProduct() {

        doNothing().when(productRepository).deleteById(1L);

        String result = productService.deleteProduct(1L);

        assertEquals("Deleted", result);
        verify(productRepository, times(1)).deleteById(1L);
    }

    // ✅ TEST: GET STOCK
    @Test
    void testGetStock() {

        Product product = new Product();
        product.setId(1L);
        product.setStock(20);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Integer stock = productService.getStock(1L);

        assertEquals(20, stock);
    }

    // ✅ TEST: REDUCE STOCK SUCCESS
    @Test
    void testReduceStockSuccess() {

        Product product = new Product();
        product.setId(1L);
        product.setStock(20);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.reduceStock(1L, 5);

        assertEquals(15, product.getStock());
        verify(productRepository, times(1)).save(product);
    }

    // ❌ TEST: REDUCE STOCK FAILURE
    @Test
    void testReduceStockFailure() {

        Product product = new Product();
        product.setId(1L);
        product.setStock(5);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.reduceStock(1L, 10);
        });

        assertEquals("Insufficient stock", exception.getMessage());
    }
}