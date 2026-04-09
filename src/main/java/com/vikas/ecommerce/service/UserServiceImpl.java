package com.vikas.ecommerce.service;

import com.vikas.ecommerce.DTO.RegisterRequestDTO;
import com.vikas.ecommerce.DTO.RegisteredResponseDTO;
import com.vikas.ecommerce.Role;
import com.vikas.ecommerce.entities.User;
import com.vikas.ecommerce.exceptions.DuplicateEmailException;
import com.vikas.ecommerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    //inject user repo
    private final UserRepository userRepository;
    //inject password encoder
    private final PasswordEncoder passwordEncoder;
     //inject jwt token
    private final jwtUtil jwtUtil;
    //inject mapper
    private final ModelMapper modelMapper;

    @Override
    public RegisteredResponseDTO createUser(RegisterRequestDTO registerRequestDTO)  {
        if(userRepository.findByEmail(registerRequestDTO.getEmail()).isPresent())
            throw new DuplicateEmailException("User already exists");

        User user=modelMapper.map(registerRequestDTO,User.class);
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      User savedUser=userRepository.save(user);
       return modelMapper.map(savedUser,RegisteredResponseDTO.class);
    }

    @Override
    public void promote(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));
        System.out.println("Before: " + user.getRole());
        user.setRole(Role.ADMIN);
        System.out.println("After: " + user.getRole());
        userRepository.save(user);

    }
}
