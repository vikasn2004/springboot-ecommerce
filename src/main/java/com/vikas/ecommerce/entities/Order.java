package com.vikas.ecommerce.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Table(name="orders",indexes = {
        @Index(name="idx_order_user",columnList = "user_id"),
        @Index(name="idx_order_user",columnList = "active")
})
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    User user;

    @Column(nullable = false)
    double totalPrice;

    LocalDateTime createdAt;
     @PrePersist
     public  void prePersist() {
        createdAt = LocalDateTime.now();
    }

    boolean active=true;

     @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
     List<OrderItem> orderItems;


}
