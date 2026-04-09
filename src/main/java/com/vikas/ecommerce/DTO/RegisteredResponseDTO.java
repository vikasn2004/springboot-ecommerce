package com.vikas.ecommerce.DTO;

import com.vikas.ecommerce.Role;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisteredResponseDTO {
    Long userId;
    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    Role role;
    LocalDateTime dateOfBirth;
}
