package com.ecommerce.product_service.service;


import com.ecommerce.product_service.dto.BrandRequestDTO;
import com.ecommerce.product_service.dto.BrandResponseDTO;
import com.ecommerce.product_service.dto.BrandWithProductsDTO;

import java.util.List;

public interface BrandService {

    BrandResponseDTO createBrand(BrandRequestDTO requestDTO);

    BrandResponseDTO getBrandById(Long id);

    List<BrandResponseDTO> getAllBrands();

    BrandResponseDTO updateBrand(Long id, BrandRequestDTO requestDTO);

    String deleteBrand(Long id);

    BrandWithProductsDTO getBrandWithProducts(Long brandId);
}
