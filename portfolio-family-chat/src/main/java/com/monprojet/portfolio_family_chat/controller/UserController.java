package com.monprojet.portfolio_family_chat.controller;

import com.monprojet.portfolio_family_chat.dto.UserResponse;
import com.monprojet.portfolio_family_chat.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    // GET /api/users : Récupérer la liste de tous les membres actifs pour le Chat
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllMembers() {
        return ResponseEntity.ok(userService.getAllActiveUsers());
    }
}