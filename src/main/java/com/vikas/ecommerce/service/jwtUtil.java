package com.vikas.ecommerce.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class jwtUtil {

    @Value("${SecretKey}")
     String secretKey;

    public  SecretKey getKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 *60 * 60 *10 ))
                .signWith(getKey())
                .compact();
    }


    public String getEmailFromToken(String authToken) {
        return Jwts
                .parser()
                .verifyWith((SecretKey) getKey())
                .build()
                .parseSignedClaims(authToken)
                .getPayload().getSubject();
    }
    public Date getExpirationDateFromToken(String authToken) {
        return Jwts
                .parser()
                .verifyWith((SecretKey) getKey())
                .build()
                .parseSignedClaims(authToken)
                .getPayload().getExpiration();
    }

    public boolean TokenExpired(String authToken) {
        return getExpirationDateFromToken(authToken).before(new Date());
    }
    public boolean validateToken(String authToken, UserDetails userDetails) {
        String email = getEmailFromToken(authToken);
        return email.equals(userDetails.getUsername()) && !TokenExpired(authToken);
    }
}
