package com.vikas.ecommerce.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="products")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank(message = "name of product cannot be empty")
    @Column( nullable = false)
    String name;

    @Column(length = 1000,nullable = false)
    String description;

    @Positive(message = "the price must be positive")
    @Column(nullable = false)
    double price;

    @NotBlank(message = "the brand name cannot be empty")
    @Column(nullable = false)
    String brand;

    LocalDateTime createdAt;
    @PrePersist
    public void prePersist(){
        createdAt = LocalDateTime.now();
    }
    boolean active=true;

    @OneToMany(mappedBy = "product")
    List<OrderItem> orderItem;
}
