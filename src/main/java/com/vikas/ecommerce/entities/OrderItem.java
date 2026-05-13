package com.vikas.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "order_item", indexes = {
        @Index(name="idx_orderItem_orde",columnList = "order_id"),
        @Index(name="idx_orderItem_product",columnList = "product_id")
})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id;

    long quantity;

    @Column(nullable = false)
    double price;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name="product_id" )

    Product product;

   @JsonIgnore
   @ManyToOne
    @JoinColumn(name="order_id")
    Order order;


}


