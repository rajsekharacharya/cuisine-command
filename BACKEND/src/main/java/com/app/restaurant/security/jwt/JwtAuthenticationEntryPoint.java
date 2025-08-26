package com.app.restaurant.security.jwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.app.restaurant.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

    // ObjectMapper can be injected via constructor if preferred (for easier testing)
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        // Log the authentication exception message
        logger.error("Unauthorized access error: {}", authException.getMessage());

        // Create a consistent API response body
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .status(false)
                .message("Unauthorized access")
                .error(authException.getMessage())
                .build();

        // Set HTTP status and content type headers
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        // Write the JSON serialized response to output
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}
