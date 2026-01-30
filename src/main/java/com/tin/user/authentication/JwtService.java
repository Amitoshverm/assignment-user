package com.tin.user.authentication;

import com.tin.user.dto.LoginUserDto;
import com.tin.user.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Service
public class JwtService {

    private final SecretKey secretKey;
    private final long expirationTime;

    public JwtService(@Value("${jwt.secret}") String secret,
                      @Value("${jwt.expiration}") long expirationTime) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        this.expirationTime = expirationTime;
    }
    public String generateToken(User user) {
        return Jwts.builder()
                .claim("userId", user.getId())
                .claim("username", user.getUsername())
                .claim("email", user.getEmail())
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    public Claims validateAndExtractClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
