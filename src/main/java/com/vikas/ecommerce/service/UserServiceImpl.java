package com.vikas.ecommerce.service;

import com.vikas.ecommerce.entities.User;
import com.vikas.ecommerce.exceptions.DuplicateEmailException;
import com.vikas.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    //inject user repo
    private final UserRepository userRepository;

    @Override
    public User createUser(User user)  {

        if(userRepository.findByEmail(user.getEmail()).isPresent())
            throw new DuplicateEmailException("User already exists");

        return userRepository.save(user);
    }
}
