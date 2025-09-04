package com.example.college_explorer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.college_explorer.model.User;
import com.example.college_explorer.model.UserRole;
import com.example.college_explorer.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Register admin - only one allowed, email must be unique
    public User registerAdmin(User user) {
        if (user.getRole() == null || !user.getRole().equals(UserRole.ADMIN)) {
            throw new RuntimeException("Only ADMIN role allowed for this registration");
        }

        Optional<User> existingAdmin = userRepository.findByRole(UserRole.ADMIN);
        if (existingAdmin.isPresent()) {
            throw new RuntimeException("Admin is already registered");
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Authenticate admin on login, throws exception if invalid
    public User authenticateAdmin(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        if (!user.getRole().equals(UserRole.ADMIN)) {
            throw new RuntimeException("Not an admin user");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        return user;
    }

    // Optional: Get role by email
    public String getRole(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getRole().name();
    }
    
}
