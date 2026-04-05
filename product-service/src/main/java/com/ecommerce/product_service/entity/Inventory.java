package com.ecommerce.product_service.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "inventory")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    private String location;

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
