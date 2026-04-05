package com.ecommerce.product_service.repository;



import com.ecommerce.product_service.entity.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	List<Product> findByCategory_Id(Long categoryId);

	List<Product> findByBrand_Id(Long brandId);

	List<Product> findByNameContainingIgnoreCase(String keyword);
}
