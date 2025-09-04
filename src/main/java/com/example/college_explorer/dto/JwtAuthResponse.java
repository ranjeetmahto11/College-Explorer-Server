package com.example.college_explorer.dto;

import com.example.college_explorer.model.UserRole;

public class JwtAuthResponse {
    private String token;
    private UserRole role;

    public JwtAuthResponse(String token, UserRole role) {
        this.token = token;
        this.role = role;
    }

    // Getter and Setter for token
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    // Getter and Setter for role
    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
