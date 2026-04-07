package com.vikas.ecommerce.filters;

import com.vikas.ecommerce.service.LoadUserDetailsImple;
import com.vikas.ecommerce.service.jwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class jwtFilter extends OncePerRequestFilter {
    private final jwtUtil jwtUtil;
    private final LoadUserDetailsImple loadUserDetailsImple;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       String authHeader = request.getHeader("Authorization");
       String authToken=null;
       String email=null;
       if(authHeader ==null ||  !authHeader.startsWith("Bearer ")){
           filterChain.doFilter(request,response);
           return;
       }
       if (authHeader != null && authHeader.startsWith("Bearer ")) {
           authToken = authHeader.substring(7);
           email = jwtUtil.getEmailFromToken(authToken);
       }
       if(email != null && SecurityContextHolder.getContext().getAuthentication()==null ){
           UserDetails userDetails = loadUserDetailsImple.loadUserByUsername(email);
           if(jwtUtil.validateToken(authToken,userDetails)){
               UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                                                                           userDetails,
                                                                           null,
                                                                           userDetails.getAuthorities());
               SecurityContextHolder.getContext().setAuthentication(token);
           }
       }
       filterChain.doFilter(request,response);
    }
}
