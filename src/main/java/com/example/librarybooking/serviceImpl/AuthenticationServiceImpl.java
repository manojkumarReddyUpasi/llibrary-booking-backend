package com.example.librarybooking.serviceImpl;

import com.example.librarybooking.dto.LoginRequest;
import com.example.librarybooking.dto.LoginResponse;
import com.example.librarybooking.entity.User;
import com.example.librarybooking.exception.InvalidCredentialsException;
import com.example.librarybooking.repository.UserRepository;
import com.example.librarybooking.service.AuthenticationService;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;

    public AuthenticationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public LoginResponse authenticate(LoginRequest request, String requiredRole) {
        User user = userRepository.findByEmail(request.getEmail())
                .filter(candidate -> Boolean.TRUE.equals(candidate.getActive()))
                .filter(candidate -> candidate.getPassword().equals(request.getPassword()))
                .filter(candidate -> candidate.getRoles().stream()
                        .anyMatch(role -> role.getName() != null
                                && normalizeRole(role.getName()).equals(normalizeRole(requiredRole))))
                .orElseThrow(InvalidCredentialsException::new);

        return new LoginResponse(user.getId(), user.getName(), user.getEmail(), normalizeRole(requiredRole));
    }

    private String normalizeRole(String role) {
        String normalized = role.trim().toUpperCase(Locale.ROOT);
        return normalized.startsWith("ROLE_") ? normalized.substring("ROLE_".length()) : normalized;
    }
}
