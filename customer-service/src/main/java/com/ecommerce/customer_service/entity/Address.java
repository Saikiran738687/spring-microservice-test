package com.ecommerce.customer_service.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "addresses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;

    private String city;

    private String state;

    private String zipCode;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
