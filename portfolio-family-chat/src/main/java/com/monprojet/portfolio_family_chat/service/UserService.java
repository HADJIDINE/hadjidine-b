package com.monprojet.portfolio_family_chat.service;

import com.monprojet.portfolio_family_chat.dto.UserResponse;
import com.monprojet.portfolio_family_chat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponse> getAllActiveUsers() {
        return userRepository.findAll().stream()
                .filter(u -> u.isActive()) // Ne retourner que les membres actifs
                .map(u -> UserResponse.builder()
                        .id(u.getId())
                        .nom(u.getNom())
                        .prenom(u.getPrenom())
                        .email(u.getEmail())
                        .avatarUrl(u.getAvatarUrl()) // Photo de profil
                        .role(u.getRole())
                        .active(u.isActive())
                        .build())
                .toList();
    }
}