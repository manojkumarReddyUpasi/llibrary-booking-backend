package com.example.librarybooking.service;

import com.example.librarybooking.dto.LoginRequest;
import com.example.librarybooking.dto.LoginResponse;

public interface AuthenticationService {
    LoginResponse authenticate(LoginRequest request, String requiredRole);
}
