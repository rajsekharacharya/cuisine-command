package com.app.restaurant.security.jwt;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.app.restaurant.configuration.jwt.JwtUtil;
import com.app.restaurant.configuration.jwt.TokenRevocationService;
import com.app.restaurant.service.admin.LoginHistoryService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(JwtLogoutSuccessHandler.class);

    private final TokenRevocationService tokenRevocationService;
    private final JwtUtil jwtUtil;
    private final LoginHistoryService loginHistoryService;

    public JwtLogoutSuccessHandler(TokenRevocationService tokenRevocationService,
                                  JwtUtil jwtUtil,
                                  LoginHistoryService loginHistoryService) {
        this.tokenRevocationService = tokenRevocationService;
        this.jwtUtil = jwtUtil;
        this.loginHistoryService = loginHistoryService;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        Optional<String> optionalToken = jwtUtil.extractToken(request);

        if (optionalToken.isPresent()) {
            String token = optionalToken.get();
            try {
                tokenRevocationService.revokeToken(token);
                loginHistoryService.updateLogout(token);  // update logout time or status
                logger.info("Token successfully revoked: {}", token);
                sendResponse(response, true, "Logout successful");
            } catch (Exception e) {
                logger.error("Error while revoking token: {}", e.getMessage(), e);
                sendResponse(response, false, "Error while revoking token");
            }
        } else {
            logger.warn("No token found in request");
            sendResponse(response, false, "Token Not Found");
        }

        SecurityContextHolder.clearContext(); // Clear the security context after logout
    }

    private void sendResponse(HttpServletResponse response, boolean status, String message) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);

        // Use a simple JSON string — could also use a JSON library or ApiResponse class for consistency
        String jsonResponse = String.format("{\"status\":%b,\"message\":\"%s\"}", status, message);

        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }
}
