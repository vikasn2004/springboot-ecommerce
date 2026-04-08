package com.vikas.ecommerce.service;

import com.vikas.ecommerce.entities.User;
import com.vikas.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoadUserDetailsImple implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("user not found with "+ email));
        System.out.println("Authority: ROLE_" + user.getRole().name());
    return new org.springframework.security.core.userdetails.User(
                                       user.getEmail(),
                                       user.getPassword(),
                                       List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())));

    }
}
