package com.vikas.ecommerce.controller;

import com.vikas.ecommerce.DTO.AuthResponseDTO;
import com.vikas.ecommerce.DTO.LoginRequestDTO;
import com.vikas.ecommerce.DTO.RegisterRequestDTO;
import com.vikas.ecommerce.DTO.RegisteredResponseDTO;
import com.vikas.ecommerce.entities.User;
import com.vikas.ecommerce.service.UserService;
import com.vikas.ecommerce.service.jwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<AuthResponseDTO> registerUser(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
     com.vikas.ecommerce.DTO.RegisteredResponseDTO registeredResponseDTO =userService.createUser(registerRequestDTO);
      String authToken=jwtutil.generateToken(registeredResponseDTO.getEmail());
        AuthResponseDTO authResponseDTO=new AuthResponseDTO();
        authResponseDTO.setToken(authToken);
        authResponseDTO.setRegisteredResponseDTO(registeredResponseDTO);
     return ResponseEntity.status(HttpStatus.CREATED).body(authResponseDTO);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/promote/{email}")
    public ResponseEntity<String> promoteToAdmin(@PathVariable String email){
        userService.promote(email);
        return ResponseEntity.ok("User " + email + " promoted to ADMIN");
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
