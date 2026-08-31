package com.ammazon.commons.events;

import com.ammazon.commons.enums.UserRole;

/**
 * Event published when a new user registers.
 */
public class UserRegisteredEvent extends DomainEvent {
    private String userId;
    private String email;
    private String firstName;
    private String lastName;
    private UserRole role;

    public UserRegisteredEvent() {
    }

    public UserRegisteredEvent(String userId, String email, String firstName, String lastName, UserRole role) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    @Override
    public String getEventType() {
        return "UserRegisteredEvent";
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}