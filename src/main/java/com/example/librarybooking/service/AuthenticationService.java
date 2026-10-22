package com.example.librarybooking.service;

import com.example.librarybooking.dto.LoginRequest;
import com.example.librarybooking.dto.LoginResponse;
import com.example.librarybooking.dto.RegisterRequest;

public interface AuthenticationService {
    LoginResponse authenticate(LoginRequest request, String requiredRole);

    LoginResponse register(RegisterRequest request);
}
