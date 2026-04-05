package com.ecommerce.product_service.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.product_service.dto.BrandRequestDTO;
import com.ecommerce.product_service.dto.BrandResponseDTO;
import com.ecommerce.product_service.dto.BrandWithProductsDTO;
import com.ecommerce.product_service.entity.Brand;
import com.ecommerce.product_service.repository.BrandRepository;
import com.ecommerce.product_service.service.BrandService;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public BrandResponseDTO createBrand(BrandRequestDTO requestDTO) {

        Brand brand = new Brand();
        brand.setName(requestDTO.getName());

        return mapToDTO(brandRepository.save(brand));
    }

    @Override
    public BrandResponseDTO getBrandById(Long id) {

        return mapToDTO(brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found")));
    }

    @Override
    public List<BrandResponseDTO> getAllBrands() {

        return brandRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BrandResponseDTO updateBrand(Long id, BrandRequestDTO requestDTO) {

        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        brand.setName(requestDTO.getName());

        return mapToDTO(brandRepository.save(brand));
    }

    @Override
    public String deleteBrand(Long id) {

        brandRepository.deleteById(id);
        return "Brand deleted";
    }

    @Override
    public BrandWithProductsDTO getBrandWithProducts(Long brandId) {
        return null;
    }

    private BrandResponseDTO mapToDTO(Brand brand) {

        BrandResponseDTO dto = new BrandResponseDTO();
        dto.setId(brand.getId());
        dto.setName(brand.getName());

        return dto;
    }
}
