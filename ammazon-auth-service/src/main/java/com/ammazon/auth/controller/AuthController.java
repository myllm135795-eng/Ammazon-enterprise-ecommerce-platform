package com.ammazon.auth.controller;

import com.ammazon.auth.entity.User;
import com.ammazon.auth.service.AuthService;
import com.ammazon.shared.dto.ApiResponse;
import com.ammazon.shared.dto.LoginRequest;
import com.ammazon.shared.dto.RegisterRequest;
import com.ammazon.shared.dto.TokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * Authentication API controller.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Register a new user.
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> register(@Valid @RequestBody RegisterRequest request) {
        log.info("Register endpoint called");
        User user = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(user));
    }

    /**
     * Login and get JWT token.
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(@Valid @RequestBody LoginRequest request) {
        log.info("Login endpoint called for user: {}", request.getEmail());
        TokenResponse token = authService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(ApiResponse.ok(token, "Login successful"));
    }

    /**
     * Health check endpoint.
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Auth Service is running");
    }
}