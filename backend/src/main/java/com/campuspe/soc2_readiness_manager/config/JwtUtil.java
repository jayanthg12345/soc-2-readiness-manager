package com.campuspe.soc2_readiness_manager.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private static final String ROLES_CLAIM = "roles";
    private static final int MINIMUM_SECRET_BYTES = 32;

    private final String jwtSecret;
    private final long jwtExpirationMs;

    public JwtUtil(
            @Value("${jwt.secret}") String jwtSecret,
            @Value("${jwt.expiration-ms}") long jwtExpirationMs) {
        this.jwtSecret = jwtSecret;
        this.jwtExpirationMs = jwtExpirationMs;
    }

    public String generateToken(String username, String role) {
        return generateToken(username, Collections.singletonList(role));
    }

    public String generateToken(String username, Collection<String> roles) {
        Instant now = Instant.now();
        Instant expiry = now.plusMillis(jwtExpirationMs);

        return Jwts.builder()
                .subject(username)
                .claims(Map.of(ROLES_CLAIM, normaliseRoles(roles)))
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry))
                .signWith(getSigningKey())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception ex) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public List<String> extractRoles(String token) {
        Object rolesClaim = extractAllClaims(token).get(ROLES_CLAIM);

        if (rolesClaim instanceof Collection<?> roles) {
            return roles.stream()
                    .filter(Objects::nonNull)
                    .map(Object::toString)
                    .toList();
        }

        return Collections.emptyList();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);

        if (keyBytes.length < MINIMUM_SECRET_BYTES) {
            throw new IllegalStateException("JWT secret must be at least 32 characters long");
        }

        return Keys.hmacShaKeyFor(keyBytes);
    }

    private List<String> normaliseRoles(Collection<String> roles) {
        if (roles == null) {
            return Collections.emptyList();
        }

        return roles.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(role -> !role.isEmpty())
                .distinct()
                .collect(Collectors.toList());
    }
    private final String SECRET = "my-secret-key-my-secret-key-my-secret-key"; // >= 32 chars

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // Generate JWT
    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getKey())
                .compact();
    }

    // Extract email from token
    public String extractEmail(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

}
