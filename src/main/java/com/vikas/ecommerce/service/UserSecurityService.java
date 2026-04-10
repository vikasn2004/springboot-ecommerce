package com.vikas.ecommerce.service;

import com.vikas.ecommerce.entities.Order;
import com.vikas.ecommerce.entities.User;
import com.vikas.ecommerce.repository.OrderRepository;
import com.vikas.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service("orderSecurityService")
@RequiredArgsConstructor
public class UserSecurityService {

    //inject user repo
    private final UserRepository userRepository;
    //inject order repo
    private final OrderRepository orderRepository;

   public boolean isOwner(Authentication authentication , Long UserId){
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElse(null);
        if(user == null){
            return false;
        }
    return user.getId().equals(UserId);
    }
    public boolean isOrderOwner(Authentication authentication, Long orderId) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) return false;

        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) return false;

        return order.getUser().getId().equals(user.getId());
    }

}
