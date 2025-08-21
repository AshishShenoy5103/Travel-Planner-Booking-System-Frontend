package com.travelbooking.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtGenerator {
    public String generateToken(Authentication authentication, String userType) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expiryDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expiryDate)
                .signWith(SecurityConstants.getSigningKey(), SignatureAlgorithm.HS256)
                .claim("usertype", userType)
                .compact();

        return token;
    }

    public String getUserNameFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SecurityConstants.getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public String getUserTypeFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SecurityConstants.getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("usertype").toString();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SecurityConstants.getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            throw new AuthenticationCredentialsNotFoundException("Token expired");
        } catch (JwtException ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT Token is invalid: " + ex.getMessage());
        }

    }
}
