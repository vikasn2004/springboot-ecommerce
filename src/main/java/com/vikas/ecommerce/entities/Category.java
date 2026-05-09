package com.vikas.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name="category")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="category name cannot be empty")
    @Column(nullable = false,unique = true)
    String name;

    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY)
    @JsonIgnore
     List<Product> products;

}
