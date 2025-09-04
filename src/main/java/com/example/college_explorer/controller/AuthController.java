package com.example.college_explorer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.college_explorer.configuration.JwtProvider;
import com.example.college_explorer.configuration.JwtTokenValidator;
import com.example.college_explorer.model.User;
import com.example.college_explorer.response.AuthResponse;
import com.example.college_explorer.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

     @Autowired
    private JwtTokenValidator jwtTokenValidator; // Your existing JWT validator

     @GetMapping("/verify-token")
    public ResponseEntity<?> verifyToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        String token = authHeader.substring(7);
        if (jwtTokenValidator.isValidToken(token)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    // Register admin - only one allowed
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerAdmin(@RequestBody User user) {
        try {
            User registeredAdmin = userService.registerAdmin(user);

            // Authenticate manually after registration
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    registeredAdmin.getEmail(), user.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtProvider.generateToken(registeredAdmin);
            return new ResponseEntity<>(new AuthResponse("Admin registered successfully", token, registeredAdmin.getRole().name()), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthResponse(e.getMessage(), null, null));
        }
    }

    // Admin login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginAdmin(@RequestBody User user) {
        try {
            // Authenticate using your service (checks role, password, email)
            User admin = userService.authenticateAdmin(user.getEmail(), user.getPassword());

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    admin.getEmail(), null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtProvider.generateToken(admin);
            return new ResponseEntity<>(new AuthResponse("Admin logged in successfully", token, admin.getRole().name()), HttpStatus.OK);

        } catch (RuntimeException e) {
            String msg = e.getMessage();
            HttpStatus status = HttpStatus.UNAUTHORIZED;

            if ("Admin not found".equals(msg)) {
                msg = "Admin is not registered yet.";
                status = HttpStatus.NOT_FOUND;
            } else if ("Wrong password".equals(msg)) {
                msg = "Invalid password.";
            } else if ("Not an admin user".equals(msg)) {
                msg = "User is not an admin.";
            }

            return ResponseEntity.status(status)
                    .body(new AuthResponse(msg, null, null));
        }
    }
}
