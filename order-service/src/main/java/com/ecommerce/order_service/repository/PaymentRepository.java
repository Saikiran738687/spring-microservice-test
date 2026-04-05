package com.ecommerce.order_service.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce.order_service.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
