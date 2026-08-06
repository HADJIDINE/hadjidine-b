package com.monprojet.portfolio_family_chat.controller;

import com.monprojet.portfolio_family_chat.dto.AuthResponse;
import com.monprojet.portfolio_family_chat.dto.LoginRequest;
import com.monprojet.portfolio_family_chat.dto.RegisterRequest;
import com.monprojet.portfolio_family_chat.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Permet les requêtes depuis ton frontend React
public class AuthController {

    private final AuthService authService;

    // POST /api/auth/register : Inscription avec vérification du code familial
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            AuthResponse response = authService.register(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // POST /api/auth/login : Connexion
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}