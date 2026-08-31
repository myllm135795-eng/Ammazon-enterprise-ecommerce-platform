package com.ammazon.auth.service;

import com.ammazon.auth.entity.User;
import com.ammazon.auth.repository.UserRepository;
import com.ammazon.commons.enums.UserRole;
import com.ammazon.shared.dto.RegisterRequest;
import com.ammazon.shared.dto.TokenResponse;
import com.ammazon.shared.exception.AuthenticationException;
import com.ammazon.shared.exception.ValidationException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Authentication service for handling login and registration.
 */
@Slf4j
@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.secret:your-secret-key-change-in-production-your-secret-key-change-in-production}")
    private String secret;

    @Value("${jwt.expiration:86400000}")
    private long expiration;

    /**
     * Register a new user.
     */
    public User register(RegisterRequest request) {
        log.info("Registering new user with email: {}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ValidationException("Email already exists");
        }

        if (request.getPhone() != null && userRepository.existsByPhone(request.getPhone())) {
            throw new ValidationException("Phone number already exists");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .address(request.getAddress())
                .city(request.getCity())
                .postalCode(request.getPostalCode())
                .country(request.getCountry())
                .role(UserRole.CUSTOMER)
                .active(true)
                .build();

        return userRepository.save(user);
    }

    /**
     * Authenticate user and generate JWT token.
     */
    public TokenResponse login(String email, String password) {
        log.info("Login attempt for user: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException("Invalid email or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthenticationException("Invalid email or password");
        }

        if (!user.isActive()) {
            throw new AuthenticationException("User account is inactive");
        }

        String accessToken = generateToken(user.getId(), user.getEmail(), user.getRole().getCode());
        String refreshToken = generateRefreshToken(user.getId());

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(expiration)
                .userId(user.getId())
                .build();
    }

    /**
     * Generate JWT token.
     */
    public String generateToken(String userId, String email, String role) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.builder()
                .setSubject(userId)
                .claim("email", email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    /**
     * Generate refresh token (longer expiration).
     */
    public String generateRefreshToken(String userId) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (expiration * 7)))
                .signWith(key)
                .compact();
    }
}