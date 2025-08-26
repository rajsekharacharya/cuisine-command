package com.app.restaurant.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.restaurant.configuration.MessageConstants;
import com.app.restaurant.dto.admin.AuthenticationRequest;
import com.app.restaurant.dto.admin.AuthenticationResponse;
import com.app.restaurant.response.ApiResponse;
import com.app.restaurant.service.admin.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user authentication and token management")
public class AuthController {

    private final AuthService authService;

    /**
     * Authenticates a user and returns JWT access and refresh tokens.
     *
     * @param authenticationRequest contains username/email and password
     * @param request               HTTP servlet request (to get client info)
     * @return ApiResponse with tokens map
     */
    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticates a user and returns a JWT token for authorized access.")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> loginUser(
            @RequestBody AuthenticationRequest authenticationRequest,
            HttpServletRequest request) {
        AuthenticationResponse response = authService.login(authenticationRequest, request);
        return ResponseEntity.ok(ApiResponse.<AuthenticationResponse>builder()
                .status(true)
                .message(MessageConstants.ACTION_SUCCESS)
                .data(response)
                .build());
    }

    /**
     * Refresh JWT tokens using a valid refresh token.
     *
     * @param token   refresh token
     * @param request HTTP servlet request
     * @return ApiResponse with new access token and existing refresh token
     */
    @PostMapping("/refresh")
    @Operation(summary = "Refresh Token", description = "Refresh token to regenerate JWT token for authorized access.")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> refresh(
            @RequestParam("token") String token,
            HttpServletRequest request) {
        AuthenticationResponse response = authService.refreshLogin(token, request);
        return ResponseEntity.ok(ApiResponse.<AuthenticationResponse>builder()
                .status(true)
                .message(MessageConstants.ACTION_SUCCESS)
                .data(response)
                .build());
    }
}
