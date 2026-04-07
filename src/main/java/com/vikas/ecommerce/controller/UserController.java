package com.vikas.ecommerce.controller;

import com.vikas.ecommerce.entities.User;
import com.vikas.ecommerce.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ecommerce")
public class UserController {

    //inject the userserice interface
    private final UserService userService;

    @PostMapping("/user/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user){
      String authToken=userService.createUser(user);
     return ResponseEntity.status(HttpStatus.CREATED).body(authToken);
    }

}
