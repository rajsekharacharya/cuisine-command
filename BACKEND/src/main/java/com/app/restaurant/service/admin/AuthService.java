package com.app.restaurant.service.admin;

import com.app.restaurant.dto.admin.AuthenticationRequest;
import com.app.restaurant.dto.admin.AuthenticationResponse;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest, HttpServletRequest request);
    public AuthenticationResponse refreshLogin(String refreshToken, HttpServletRequest request);
}
