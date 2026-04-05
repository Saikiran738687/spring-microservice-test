package com.ecommerce.order_service.feign;

import com.ecommerce.order_service.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("/api/products/{id}")
    ProductResponse getProductById(@PathVariable Long id);

    @GetMapping("/api/products/inventory/{productId}")
    Integer getStock(@PathVariable Long productId);

    @PostMapping("/api/products/inventory/reduce")
    void reduceStock(@RequestParam Long productId,
                     @RequestParam Integer quantity);

    // 🔥 NEW: RESTORE STOCK
    @PostMapping("/api/products/inventory/increase")
    void increaseStock(@RequestParam Long productId,
                       @RequestParam Integer quantity);
}