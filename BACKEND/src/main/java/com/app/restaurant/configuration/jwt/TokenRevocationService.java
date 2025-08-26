package com.app.restaurant.configuration.jwt;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenRevocationService {

    private static final Logger logger = LoggerFactory.getLogger(TokenRevocationService.class);

    private final JwtUtil jwtUtil;

    // Use a thread-safe set to avoid concurrency issues
    private final Set<String> revokedTokens = ConcurrentHashMap.newKeySet();

    /**
     * Checks if the given token has been revoked.
     * 
     * @param token JWT token string
     * @return true if revoked, false otherwise
     */
    public boolean isTokenRevoked(String token) {
        return revokedTokens.contains(token);
    }

    /**
     * Revokes the given JWT token.
     * 
     * @param token JWT token string
     */
    public void revokeToken(String token) {
        revokedTokens.add(token);
        logger.info("Token revoked: {}", token);
    }

    /**
     * Scheduled task to cleanup expired tokens from the revoked tokens set.
     * Runs daily at midnight.
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanupExpiredTokens() {
        logger.info("Starting cleanup of expired revoked tokens.");

        Iterator<String> iterator = revokedTokens.iterator();
        Date now = new Date();

        while (iterator.hasNext()) {
            String token = iterator.next();
            try {
                Date expiration = jwtUtil.extractExpiration(token);
                if (expiration.before(now)) {
                    iterator.remove();
                    logger.info("Removed expired revoked token: {}", token);
                }
            } catch (Exception e) {
                logger.error("Failed to check expiration for token: {}. Error: {}", token, e.getMessage());
                // Optional: remove invalid tokens to prevent indefinite retention
                // iterator.remove();
            }
        }

        logger.info("Expired token cleanup completed.");
    }
}
