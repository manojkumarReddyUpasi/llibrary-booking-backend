package com.example.librarybooking.serviceImpl;

import com.example.librarybooking.dto.LoginRequest;
import com.example.librarybooking.dto.LoginResponse;
import com.example.librarybooking.dto.RegisterRequest;
import com.example.librarybooking.entity.Role;
import com.example.librarybooking.entity.User;
import com.example.librarybooking.exception.DuplicateResourceException;
import com.example.librarybooking.repository.RoleRepository;
import org.springframework.transaction.annotation.Transactional;
import com.example.librarybooking.exception.InvalidCredentialsException;
import com.example.librarybooking.repository.UserRepository;
import com.example.librarybooking.service.AuthenticationService;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AuthenticationServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public LoginResponse authenticate(LoginRequest request, String requiredRole) {
        String email = request.getEmail().trim();
        User user = userRepository.findByEmailIgnoreCase(email)
                .filter(candidate -> Boolean.TRUE.equals(candidate.getActive()))
                .filter(candidate -> candidate.getPassword().equals(request.getPassword()))
                .filter(candidate -> candidate.getRoles().stream()
                        .anyMatch(role -> role.getName() != null
                                && normalizeRole(role.getName()).equals(normalizeRole(requiredRole))))
                .orElseThrow(InvalidCredentialsException::new);

        return new LoginResponse(user.getId(), user.getName(), user.getEmail(), normalizeRole(requiredRole));
    }

    @Override
    @Transactional
    public LoginResponse register(RegisterRequest request) {
        String email = request.getEmail().trim().toLowerCase(Locale.ROOT);
        if (userRepository.findByEmailIgnoreCase(email).isPresent()) {
            throw new DuplicateResourceException("Email already registered");
        }

        Role userRole = roleRepository.findByName("USER")
                .or(() -> roleRepository.findByName("ROLE_USER"))
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("USER");
                    return roleRepository.save(role);
                });

        User user = new User(request.getName().trim(), email, request.getPassword());
        user.setActive(true);
        user.getRoles().add(userRole);
        user = userRepository.save(user);

        return new LoginResponse(user.getId(), user.getName(), user.getEmail(), "USER");
    }

    private String normalizeRole(String role) {
        String normalized = role.trim().toUpperCase(Locale.ROOT);
        return normalized.startsWith("ROLE_") ? normalized.substring("ROLE_".length()) : normalized;
    }
}
