package com.vikas.ecommerce.controller;

import com.vikas.ecommerce.DTO.LoginRequestDTO;
import com.vikas.ecommerce.entities.User;
import com.vikas.ecommerce.service.UserService;
import com.vikas.ecommerce.service.jwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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
   //inject authentication
    private final AuthenticationManager authenticationManager;
    //inject jwt
    private final jwtUtil jwtutil;

    @PostMapping("/user/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user){
      User SavedUser=userService.createUser(user);
      String authToken=jwtutil.generateToken(SavedUser.getEmail());
     return ResponseEntity.status(HttpStatus.CREATED).body(authToken);
    }

    @PostMapping("/user/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO){
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                                                (loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String authToken = jwtutil.generateToken(userDetails.getUsername());

        return new ResponseEntity<>(authToken, HttpStatus.OK);
    }
}
