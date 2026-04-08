package com.vikas.ecommerce.service;

import com.vikas.ecommerce.Role;
import com.vikas.ecommerce.entities.User;
import com.vikas.ecommerce.exceptions.DuplicateEmailException;
import com.vikas.ecommerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    //inject user repo
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final jwtUtil jwtUtil;

    @Override
    public User createUser(User user)  {

        if(userRepository.findByEmail(user.getEmail()).isPresent())
            throw new DuplicateEmailException("User already exists");

        user.setPassword(passwordEncoder.encode(user.getPassword()));


        return userRepository.save(user);

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
