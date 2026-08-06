package com.monprojet.portfolio_family_chat.service;

import com.monprojet.portfolio_family_chat.dto.UserResponse;
import com.monprojet.portfolio_family_chat.entity.FamilyCode;
import com.monprojet.portfolio_family_chat.entity.Role;
import com.monprojet.portfolio_family_chat.entity.User;
import com.monprojet.portfolio_family_chat.repository.FamilyCodeRepository;
import com.monprojet.portfolio_family_chat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final FamilyCodeRepository familyCodeRepository;
    private final PasswordEncoder passwordEncoder; // Injecté pour hacher le mot de passe

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(u -> UserResponse.builder()
                        .id(u.getId())
                        .nom(u.getNom())
                        .prenom(u.getPrenom())
                        .email(u.getEmail())
                        .avatarUrl(u.getAvatarUrl()) // Ajout de l'URL/chemin de la photo de profil
                        .role(u.getRole())
                        .active(u.isActive())
                        .build())
                .toList();
    }

    public void toggleUserActive(Long userId, String currentUserEmail) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Sécurité : Interdire la désactivation si c'est son propre compte ou un compte ADMIN
        if (user.getEmail().equalsIgnoreCase(currentUserEmail)) {
            throw new RuntimeException("Vous ne pouvez pas désactiver votre propre compte !");
        }
        if (user.getRole() == Role.ROLE_ADMIN) {
            throw new RuntimeException("Impossible de désactiver un compte Administrateur !");
        }

        user.setActive(!user.isActive());
        userRepository.save(user);
    }

    public void deleteUser(Long userId, String currentUserEmail) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Sécurité : Interdire la suppression de soi-même ou d'un ADMIN
        if (user.getEmail().equalsIgnoreCase(currentUserEmail)) {
            throw new RuntimeException("Vous ne pouvez pas supprimer votre propre compte !");
        }
        if (user.getRole() == Role.ROLE_ADMIN) {
            throw new RuntimeException("Impossible de supprimer un compte Administrateur !");
        }

        userRepository.deleteById(userId);
    }

    // Réinitialisation du mot de passe
    public void resetPassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public FamilyCode updateFamilyCode(String newCode) {
        familyCodeRepository.deleteAll();
        FamilyCode familyCode = new FamilyCode();
        familyCode.setCode(newCode);
        familyCode.setActive(true);
        return familyCodeRepository.save(familyCode);
    }
}