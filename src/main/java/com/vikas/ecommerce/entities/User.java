package com.vikas.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vikas.ecommerce.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="users")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    String firstName;

    String lastName;

    @Column(unique = true ,nullable = false)
    @Email(message = "Invalid email format")
    @NotBlank
    String email;

    @NotBlank
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$",
            message = "Password must be 8+ chars, include uppercase, lowercase, number, and special character"
    )
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    String password;

    @Column(unique = true ,nullable = false)
    @Pattern(regexp = "^([0-9]){10}$",message = "phone number must be 10 digits")
    String phoneNumber;

    @Enumerated(EnumType.STRING)
    Roles role=Roles.USER;

    LocalDateTime createdAt;
    @PrePersist
    public void prePersist(){
        createdAt = LocalDateTime.now();
    }

    boolean active=true;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    List<Order> orders;
}
