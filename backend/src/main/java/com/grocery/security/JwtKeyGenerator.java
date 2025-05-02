package com.grocery.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Base64;

public class JwtKeyGenerator {
    public static void main(String[] args) {
        // Generate a secure key for HS256
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        // Convert the key to a Base64 encoded string for configuration
        String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());

        System.out.println("Secure JWT Secret Key: " + encodedKey);
    }
}