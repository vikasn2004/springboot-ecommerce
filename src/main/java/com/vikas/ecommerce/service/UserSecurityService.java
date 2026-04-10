package com.vikas.ecommerce.service;

import com.vikas.ecommerce.entities.User;
import com.vikas.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service("orderSecurityService")
@RequiredArgsConstructor
public class UserSecurityService {

    //inject user repo
    private final UserRepository userRepository;

    boolean isOwner(Authentication authentication , Long UserId){
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElse(null);
        if(user == null){
            return false;
        }
    return user.getId().equals(UserId);
    }

}
