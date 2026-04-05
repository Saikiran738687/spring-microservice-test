package com.ecommerce.product_service.service;


import com.ecommerce.product_service.dto.SupplierRequestDTO;
import com.ecommerce.product_service.dto.SupplierResponseDTO;
import com.ecommerce.product_service.dto.SupplierWithProductsDTO;

import java.util.List;

public interface SupplierService {

    SupplierResponseDTO createSupplier(SupplierRequestDTO requestDTO);

    SupplierResponseDTO getSupplierById(Long id);

    List<SupplierResponseDTO> getAllSuppliers();

    SupplierResponseDTO updateSupplier(Long id, SupplierRequestDTO requestDTO);

    String deleteSupplier(Long id);

    SupplierWithProductsDTO getSupplierWithProducts(Long supplierId);
}
