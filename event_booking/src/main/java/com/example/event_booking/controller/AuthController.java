package com.example.event_booking.controller;

import com.example.event_booking.dto.RegisterRequest;
import com.example.event_booking.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import com.example.event_booking.dto.AuthenticationResponse;
import com.example.event_booking.dto.LoginRequest;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(
            @Valid @RequestBody LoginRequest request) {

        return authService.login(request);

    }
}