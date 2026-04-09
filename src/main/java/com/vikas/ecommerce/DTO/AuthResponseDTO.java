package com.vikas.ecommerce.DTO;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthResponseDTO {
    String token;
    RegisteredResponseDTO registeredResponseDTO;
}
