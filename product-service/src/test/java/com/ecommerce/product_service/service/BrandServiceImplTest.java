package com.ecommerce.product_service.service;

import com.ecommerce.product_service.dto.BrandRequestDTO;
import com.ecommerce.product_service.dto.BrandResponseDTO;
import com.ecommerce.product_service.entity.Brand;
import com.ecommerce.product_service.repository.BrandRepository;
import com.ecommerce.product_service.serviceImpl.BrandServiceImpl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BrandServiceImplTest {

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private BrandServiceImpl brandService;

    public BrandServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    // ✅ CREATE BRAND
    @Test
    void testCreateBrand() {

        BrandRequestDTO request = new BrandRequestDTO();
        request.setName("Apple");

        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Apple");

        when(brandRepository.save(any(Brand.class))).thenReturn(brand);

        BrandResponseDTO result = brandService.createBrand(request);

        assertNotNull(result);
        assertEquals("Apple", result.getName());
    }

    // ✅ GET BRAND BY ID
    @Test
    void testGetBrandById() {

        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Samsung");

        when(brandRepository.findById(1L)).thenReturn(Optional.of(brand));

        BrandResponseDTO result = brandService.getBrandById(1L);

        assertNotNull(result);
        assertEquals("Samsung", result.getName());
    }

    // ❌ GET BRAND NOT FOUND
    @Test
    void testGetBrandById_NotFound() {

        when(brandRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            brandService.getBrandById(1L);
        });

        assertEquals("Brand not found", exception.getMessage());
    }

    // ✅ GET ALL BRANDS
    @Test
    void testGetAllBrands() {
    	Brand b1 = new Brand();
    	b1.setId(1L);
    	b1.setName("Apple");

    	Brand b2 = new Brand();
    	b2.setId(2L);
    	b2.setName("Samsung");

       

        when(brandRepository.findAll()).thenReturn(Arrays.asList(b1, b2));

        assertEquals(2, brandService.getAllBrands().size());
    }

    // ✅ UPDATE BRAND
    @Test
    void testUpdateBrand() {

        Brand existing = new Brand();
        existing.setId(1L);
        existing.setName("Old");

        BrandRequestDTO request = new BrandRequestDTO();
        request.setName("New");

        when(brandRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(brandRepository.save(any(Brand.class))).thenReturn(existing);

        BrandResponseDTO result = brandService.updateBrand(1L, request);

        assertEquals("New", result.getName());
    }

    // ❌ UPDATE BRAND NOT FOUND
    @Test
    void testUpdateBrand_NotFound() {

        BrandRequestDTO request = new BrandRequestDTO();
        request.setName("New");

        when(brandRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            brandService.updateBrand(1L, request);
        });

        assertEquals("Brand not found", exception.getMessage());
    }

    // ✅ DELETE BRAND
    @Test
    void testDeleteBrand() {

        doNothing().when(brandRepository).deleteById(1L);

        String result = brandService.deleteBrand(1L);

        assertEquals("Brand deleted", result);
        verify(brandRepository, times(1)).deleteById(1L);
    }
}