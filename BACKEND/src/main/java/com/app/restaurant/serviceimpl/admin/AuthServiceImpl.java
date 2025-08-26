package com.app.restaurant.serviceimpl.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.app.restaurant.configuration.MessageConstants;
import com.app.restaurant.configuration.jwt.JwtUtil;
import com.app.restaurant.dto.admin.AuthenticationRequest;
import com.app.restaurant.dto.admin.AuthenticationResponse;
import com.app.restaurant.enums.LoginStatus;
import com.app.restaurant.exception.JwtAuthenticationException;
import com.app.restaurant.exception.ResourceNotFoundException;
import com.app.restaurant.model.admin.Users;
import com.app.restaurant.repository.admin.UsersRepository;
import com.app.restaurant.service.admin.AuthService;
import com.app.restaurant.service.admin.LoginHistoryService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final LoginHistoryService loginHistoryService;
    private final UsersRepository usersRepository;

    @Override
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest, HttpServletRequest request) {
        String identifier = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();

        logger.info("Login attempt for identifier: {}", identifier);

        if (identifier == null || identifier.isBlank() || password == null || password.isBlank()) {
            logger.warn("Login failed due to missing credentials for identifier: {}", identifier);
            throw new BadCredentialsException(MessageConstants.INVALID_CREDENTIALS);
        }

        this.doAuthenticate(identifier, password);

        Users user = usersRepository.findByUsernameOrEmail(identifier,identifier)
                .orElseThrow(() -> {
                    logger.warn("User not found for identifier: {}", identifier);
                    return new ResourceNotFoundException(MessageConstants.COMMON_NOT_FOUND);
                });

        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        user.setRefreshToken(refreshToken);
        usersRepository.save(user);

        String ip = request.getRemoteAddr();
        String device = request.getHeader("User-Agent");

        loginHistoryService.saveLoginHistory(user, accessToken, ip, device, LoginStatus.SUCCESS);

        logger.info("User {} logged in successfully from IP: {}", identifier, ip);

        return new AuthenticationResponse(accessToken, refreshToken);
    }

    @Override
    public AuthenticationResponse refreshLogin(String refreshToken, HttpServletRequest request) {
        logger.info("Refresh token attempt");

        if (refreshToken == null || refreshToken.isBlank()) {
            logger.warn("Refresh token is null or blank");
            throw new JwtAuthenticationException(MessageConstants.INVALID_REFRESH_TOKEN);
        }


        if (jwtUtil.isTokenExpired(refreshToken) || !jwtUtil.validateToken(refreshToken)) {
            logger.warn("Invalid or expired refresh token");
            throw new JwtAuthenticationException(MessageConstants.INVALID_REFRESH_TOKEN);
        }

        String username = jwtUtil.extractUsername(refreshToken);
        Users user = usersRepository.findByUsernameOrEmail(username,username)
                .orElseThrow(() -> {
                    logger.warn("User not found for refresh token username: {}", username);
                    return new ResourceNotFoundException(MessageConstants.COMMON_NOT_FOUND);
                });

        if (!refreshToken.equals(user.getRefreshToken())) {
            logger.warn("Refresh token mismatch for user: {}", username);
            throw new JwtAuthenticationException(MessageConstants.INVALID_REFRESH_TOKEN);
        }

        String accessToken = jwtUtil.generateAccessToken(user);

        logger.info("Access token refreshed for user: {}", username);

        return new AuthenticationResponse(accessToken, refreshToken);
    }

    private void doAuthenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (AuthenticationException e) {
            logger.warn("Authentication failed for username: {}", username);
            throw new BadCredentialsException(MessageConstants.INVALID_CREDENTIALS);
        }
    }
}
