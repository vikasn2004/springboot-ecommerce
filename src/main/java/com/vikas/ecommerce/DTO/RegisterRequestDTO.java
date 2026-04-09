package com.vikas.ecommerce.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRequestDTO {
    @Column(nullable = false)
    String firstName;

    String lastName;

    @Column(nullable = false,unique = true)
    @Email(message = "email is not valid!")
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
}
