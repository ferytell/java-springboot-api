
package com.example.controller;

import com.example.dto.AuthRequest;
import com.example.dto.AuthResponse;
import com.example.dto.RegisterRequest;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.util.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
            String token = jwtUtil.generateToken(userDetails);

            User user = userRepository.findByUsername(request.getUsername())
                    .or(() -> userRepository.findByEmail(request.getUsername()))
                    .orElseThrow(() -> new RuntimeException("User not found"));

            return ResponseEntity.ok(new AuthResponse(token, user.getUsername(), user.getRole()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid username or password"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Username already taken"));
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Email already registered"));
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setName(request.getFullName());
        user.setRole("ROLE_USER");

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "User registered successfully"));
    }
}