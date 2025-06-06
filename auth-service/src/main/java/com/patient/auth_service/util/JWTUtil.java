package com.patient.auth_service.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JWTUtil {

    private final Key secretKey;
    private final long expiration = 1000*60*60*10;

    public JWTUtil(@Value("${jwt.secret}") String secretKey) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String email, String role){
        return Jwts.builder().subject(email).claim("role", role)
                .issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()+expiration))
                .signWith(secretKey).compact();
    }

    public void validateToken(String token){
        try {
            Jwts.parser().verifyWith((SecretKey) secretKey).build().parseSignedClaims(token);
        }catch (SignatureException e){
            throw new SignatureException("Invalid JWT signature");
        }catch (JwtException e){
            throw new JwtException("Invalid JWT token");
        }
    }


}
