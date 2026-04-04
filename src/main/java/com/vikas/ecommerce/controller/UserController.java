package com.vikas.ecommerce.controller;

import com.vikas.ecommerce.entities.User;
import com.vikas.ecommerce.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    //inject the userserice interface
    private final UserService userService;

    @PostMapping("/register")
    public User registerUser(@Valid @RequestBody User user){
        return userService.createUser(user);
    }

}
