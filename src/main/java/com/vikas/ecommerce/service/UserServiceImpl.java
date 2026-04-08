package com.vikas.ecommerce.service;

import com.vikas.ecommerce.entities.User;
import com.vikas.ecommerce.exceptions.DuplicateEmailException;
import com.vikas.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
}
