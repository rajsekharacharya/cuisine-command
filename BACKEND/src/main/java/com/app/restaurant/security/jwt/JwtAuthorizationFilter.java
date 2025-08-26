package com.app.restaurant.security.jwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.restaurant.configuration.jwt.JwtUtil;
import com.app.restaurant.configuration.jwt.TokenRevocationService;
import com.app.restaurant.security.MyUserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    private final JwtUtil jwtUtil;
    private final MyUserService myUserService;
    private final TokenRevocationService tokenRevocationService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain) throws ServletException, IOException {

        // Extract token from request header and validate it
        jwtUtil.extractToken(request)
            .filter(token -> {
                boolean notRevoked = !tokenRevocationService.isTokenRevoked(token);
                boolean isAccess = jwtUtil.isAccessToken(token);
                boolean valid = jwtUtil.validateToken(token);
                if (!notRevoked) log.debug("Token is revoked");
                if (!isAccess) log.debug("Token is not an access token");
                if (!valid) log.debug("Token is invalid");
                return notRevoked && isAccess && valid;
            })
            .ifPresent(token -> processTokenAuthentication(token, request));

        // Continue the filter chain whether authenticated or not
        chain.doFilter(request, response);
    }

    private void processTokenAuthentication(String token, HttpServletRequest request) {
        try {
            String username = jwtUtil.extractUsername(token);
            UserDetails userDetails = myUserService.loadUserByUsername(username);

            // Build Authentication token for Spring Security context
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.debug("Authenticated user: {}", username);
        } catch (Exception e) {
            log.error("Authentication failed: {}", e.getMessage());
        }
    }
}
