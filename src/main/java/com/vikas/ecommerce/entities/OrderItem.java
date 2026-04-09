package com.vikas.ecommerce.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id;

    long quantity;

    @Column(nullable = false)
    double price;

   @ManyToOne
   @JoinColumn(name="product_id")
    Product product;

   @ManyToOne
    @JoinColumn(name="order_id")
    Order order;


}


