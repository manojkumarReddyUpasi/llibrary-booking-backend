package com.example.librarybooking.controller;

import com.example.librarybooking.dto.LoginRequest;
import com.example.librarybooking.dto.LoginResponse;
import com.example.librarybooking.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/user/login")
    public LoginResponse userLogin(@Valid @RequestBody LoginRequest request) {
        return authenticationService.authenticate(request, "USER");
    }

    @PostMapping("/admin/login")
    public LoginResponse adminLogin(@Valid @RequestBody LoginRequest request) {
        return authenticationService.authenticate(request, "ADMIN");
    }
}
