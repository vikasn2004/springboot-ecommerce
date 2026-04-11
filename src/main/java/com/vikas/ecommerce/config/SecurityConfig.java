package com.vikas.ecommerce.config;

import com.vikas.ecommerce.filters.jwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig    {

    private final jwtFilter jwtfilter;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                                .requestMatchers("/ecommerce/user/**").permitAll()
                        .requestMatchers("/swagger-ui/**" , "/v3/api-docs","/swagger-ui.html").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/ecommerce/admin/promote/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST , "/ecommerce/products/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET , "/ecommerce/products/**" ).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT , "/ecommerce/products/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/ecommerce/products/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/ecommerce/orders/{userId}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/ecommerce/orders").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET,  "/ecommerce/orders/all").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/ecommerce/orders/**").hasAnyRole("ADMIN", "USER")
                        .anyRequest()
                        .authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtfilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
