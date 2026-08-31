package com.ammazon.user.controller;

import com.ammazon.user.service.UserService;
import com.ammazon.shared.dto.ApiResponse;
import com.ammazon.shared.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User API controller.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Get user profile by ID.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable String userId) {
        log.info("Get user endpoint called for userId: {}", userId);
        UserDto userDto = userService.getUserById(userId);
        return ResponseEntity.ok(ApiResponse.ok(userDto));
    }

    /**
     * Update user profile.
     */
    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserDto>> updateUser(@PathVariable String userId,
                                                           @RequestBody UserDto userDto) {
        log.info("Update user endpoint called for userId: {}", userId);
        UserDto updated = userService.updateUser(userId, userDto);
        return ResponseEntity.ok(ApiResponse.ok(updated, "User updated successfully"));
    }

    /**
     * Get all users.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers() {
        log.info("Get all users endpoint called");
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.ok(users));
    }

    /**
     * Health check endpoint.
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("User Service is running");
    }
}