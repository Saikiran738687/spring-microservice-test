package com.ecommerce.order_service.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paymentMethod; // CARD, UPI, COD

    private String paymentStatus; // SUCCESS, FAILED, PENDING

    private Double amount;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
