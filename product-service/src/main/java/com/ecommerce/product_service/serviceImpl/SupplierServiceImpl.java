package com.ecommerce.product_service.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.product_service.dto.SupplierRequestDTO;
import com.ecommerce.product_service.dto.SupplierResponseDTO;
import com.ecommerce.product_service.dto.SupplierWithProductsDTO;
import com.ecommerce.product_service.entity.Supplier;
import com.ecommerce.product_service.repository.SupplierRepository;
import com.ecommerce.product_service.service.SupplierService;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public SupplierResponseDTO createSupplier(SupplierRequestDTO requestDTO) {

        Supplier supplier = new Supplier();
        supplier.setName(requestDTO.getName());

        return mapToDTO(supplierRepository.save(supplier));
    }

    @Override
    public SupplierResponseDTO getSupplierById(Long id) {

        return mapToDTO(supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found")));
    }

    @Override
    public List<SupplierResponseDTO> getAllSuppliers() {

        return supplierRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SupplierResponseDTO updateSupplier(Long id, SupplierRequestDTO requestDTO) {

        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        supplier.setName(requestDTO.getName());

        return mapToDTO(supplierRepository.save(supplier));
    }

    @Override
    public String deleteSupplier(Long id) {

        supplierRepository.deleteById(id);
        return "Supplier deleted";
    }

    @Override
    public SupplierWithProductsDTO getSupplierWithProducts(Long supplierId) {
        return null;
    }

    private SupplierResponseDTO mapToDTO(Supplier supplier) {

        SupplierResponseDTO dto = new SupplierResponseDTO();
        dto.setId(supplier.getId());
        dto.setName(supplier.getName());

        return dto;
    }
}
