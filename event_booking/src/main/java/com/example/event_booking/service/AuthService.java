package com.example.event_booking.service;

import com.example.event_booking.dto.RegisterRequest;
import com.example.event_booking.exception.BadRequestException;
import com.example.event_booking.model.User;
import com.example.event_booking.repository.UserRepository;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.event_booking.dto.AuthenticationResponse;
import com.example.event_booking.dto.LoginRequest;
import com.example.event_booking.security.JwtService;

import org.springframework.security.authentication.AuthenticationManager;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public String register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
                throw new BadRequestException("Email already exists");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
        logger.info("New user registered: {}", user.getEmail());

        return "User registered successfully";
    }

    public AuthenticationResponse login(LoginRequest request) {

        logger.info("Login attempt: {}", request.getEmail());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Invalid email or password"));

        String jwtToken = jwtService.generateToken(user.getEmail());
        logger.info("Login successful: {}", user.getEmail());

        return new AuthenticationResponse(
                jwtToken,
                user.getEmail(),
                user.getRole()
        );
    }
}