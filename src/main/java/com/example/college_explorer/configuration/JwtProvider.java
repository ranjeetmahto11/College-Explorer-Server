package com.example.college_explorer.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

import com.example.college_explorer.model.User;

@Service
public class JwtProvider {

    private final SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
    private final long expirationMillis = 86400000L; // 1 day

    // ✅ Existing method to generate token from User
    public String generateToken(User user) {
        return buildToken(user.getEmail());
    }

    // ✅ New method to generate token from Authentication
    public String generateToken(Authentication authentication) {
        String email = authentication.getName(); // Principal username/email
        return buildToken(email);
    }

    // ✅ Shared logic
    private String buildToken(String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMillis);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public String getEmailFromJwtToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
