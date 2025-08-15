package com.travelbooking.security;

import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;

public class SecurityConstants {
    public static final long JWT_EXPIRATION = 3600000;
    public static final String JWT_SECRET = "ihavealongjwtsecretwordforareason";

    public static Key getSigningKey() {
        return Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
    }

}
