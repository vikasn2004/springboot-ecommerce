package com.vikas.ecommerce.service;

import com.vikas.ecommerce.DTO.RegisterRequestDTO;
import com.vikas.ecommerce.DTO.RegisteredResponseDTO;
import com.vikas.ecommerce.entities.User;

public interface UserService {
    RegisteredResponseDTO createUser(RegisterRequestDTO registerRequestDTO) ;

    void promote(String email);
}
