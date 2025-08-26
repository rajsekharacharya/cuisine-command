package com.app.restaurant.configuration.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;
import java.util.logging.Logger;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.app.restaurant.exception.JwtAuthenticationException;
import com.app.restaurant.model.admin.Users;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtil {

    private static final Logger logger = Logger.getLogger(JwtUtil.class.getName());

    private final SecretKey key;

    private static final long ACCESS_TOKEN_EXPIRATION = 60_000L; // 5 minutes
    // private static final long ACCESS_TOKEN_EXPIRATION = 86_400_000L; // 1 day
    private static final long REFRESH_TOKEN_EXPIRATION = 604_800_000L; // 7 days

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.audience}")
    private String audience;

    public JwtUtil(@Value("${secret.key}") String secretKey) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(secretKey);
            this.key = new SecretKeySpec(keyBytes, "HmacSHA256");
            logger.info("JWT secret key initialized successfully.");
        } catch (IllegalArgumentException e) {
            logger.severe("Failed to decode the secret key. Ensure it is base64 encoded.");
            throw new IllegalStateException("Failed to decode the secret key. Ensure it is base64 encoded.", e);
        }
    }

    public String generateAccessToken(Users user) {
        logger.fine("Generating access token for user: " + user.getEmail());
        return createAccessToken(user);
    }

    public String generateRefreshToken(Users user) {
        logger.fine("Generating refresh token for user: " + user.getEmail());
        return createRefreshToken(user);
    }

    private String createAccessToken(Users user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .claim("Authorities", user.getRole().name())
                .claim("issuer", issuer)
                .claim("audience", audience)
                .claim("type", "access")
                .signWith(key)
                .compact();
    }

    private String createRefreshToken(Users user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .claim("type", "refresh")
                .claim("issuer", issuer)
                .claim("audience", audience)
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            logger.warning("JWT token has expired: " + e.getMessage());
            throw new JwtAuthenticationException("JWT token has expired");
        } catch (JwtException e) {
            logger.warning("Invalid JWT token: " + e.getMessage());
            throw new JwtAuthenticationException("Invalid JWT token");
        } catch (Exception e) {
            logger.severe("Error parsing JWT token: " + e.getMessage());
            throw new JwtAuthenticationException("Invalid token");
        }
    }

    public boolean isTokenExpired(String token) {
        final Date expiration = extractExpiration(token);
        return expiration.before(new Date());
    }

    public Optional<String> extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return Optional.of(header.substring(7));
        } else {
            return Optional.empty();
        }
    }

    public boolean validateToken(String token) {
        Claims claims = extractAllClaims(token);
        boolean isValid = !claims.getExpiration().before(new Date())
                && issuer.equals(claims.get("issuer"))
                && audience.equals(claims.get("audience"));
        if (!isValid) {
            logger.warning("JWT validation failed.");
        }
        return isValid;
    }

    private String getTokenType(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("type", String.class);
    }

    public boolean isRefreshToken(String token) {
        return "refresh".equalsIgnoreCase(getTokenType(token));
    }

    public boolean isAccessToken(String token) {
        return "access".equalsIgnoreCase(getTokenType(token));
    }
}
