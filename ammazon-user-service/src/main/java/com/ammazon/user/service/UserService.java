package com.ammazon.user.service;

import com.ammazon.user.entity.UserProfile;
import com.ammazon.user.repository.UserProfileRepository;
import com.ammazon.shared.dto.UserDto;
import com.ammazon.shared.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * User service for managing user profiles.
 */
@Slf4j
@Service
public class UserService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    /**
     * Get user profile by ID.
     */
    public UserDto getUserById(String userId) {
        log.info("Getting user profile for userId: {}", userId);
        UserProfile userProfile = userProfileRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
        return mapToDto(userProfile);
    }

    /**
     * Update user profile.
     */
    public UserDto updateUser(String userId, UserDto userDto) {
        log.info("Updating user profile for userId: {}", userId);
        UserProfile userProfile = userProfileRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        userProfile.setFirstName(userDto.getFirstName());
        userProfile.setLastName(userDto.getLastName());
        userProfile.setPhone(userDto.getPhone());
        userProfile.setAddress(userDto.getAddress());
        userProfile.setCity(userDto.getCity());
        userProfile.setPostalCode(userDto.getPostalCode());
        userProfile.setCountry(userDto.getCountry());

        UserProfile updated = userProfileRepository.save(userProfile);
        return mapToDto(updated);
    }

    /**
     * Get all users.
     */
    public List<UserDto> getAllUsers() {
        return userProfileRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Map UserProfile entity to DTO.
     */
    private UserDto mapToDto(UserProfile userProfile) {
        return UserDto.builder()
                .id(userProfile.getId())
                .email(userProfile.getEmail())
                .firstName(userProfile.getFirstName())
                .lastName(userProfile.getLastName())
                .phone(userProfile.getPhone())
                .address(userProfile.getAddress())
                .city(userProfile.getCity())
                .postalCode(userProfile.getPostalCode())
                .country(userProfile.getCountry())
                .role(userProfile.getRole().name())
                .active(userProfile.isActive())
                .createdAt(userProfile.getCreatedAt())
                .updatedAt(userProfile.getUpdatedAt())
                .build();
    }
}