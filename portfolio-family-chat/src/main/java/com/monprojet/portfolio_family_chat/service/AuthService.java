package com.monprojet.portfolio_family_chat.service;

import com.monprojet.portfolio_family_chat.dto.*;
import com.monprojet.portfolio_family_chat.entity.*;
import com.monprojet.portfolio_family_chat.repository.*;
import com.monprojet.portfolio_family_chat.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final FamilyCodeRepository familyCodeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthResponse register(RegisterRequest request) {
        // 1. Vérifier si l'email existe déjà
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Cet email est déjà utilisé !");
        }

        // 2. Vérifier si le code familial est valide et actif
        familyCodeRepository.findByCodeAndActiveTrue(request.getFamilyCode())
                .orElseThrow(() -> new RuntimeException("Code familial invalide ou inactif !"));

        // 3. Si aucun utilisateur n'existe encore en BDD, le premier inscrit devient ADMIN !
        Role roleAssigne = userRepository.count() == 0 ? Role.ROLE_ADMIN : Role.ROLE_USER;

        // 4. Créer et enregistrer l'utilisateur
        User user = User.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(roleAssigne)
                .active(true)
                .build();

        userRepository.save(user);

        // 5. Générer le token JWT
        String token = jwtUtils.generateToken(user.getEmail(), user.getRole().name());
        return new AuthResponse(token, user.getEmail(), user.getRole().name());
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Identifiants incorrects !"));

        if (!user.isActive()) {
            throw new RuntimeException("Votre compte a été désactivé par l'administrateur.");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Identifiants incorrects !");
        }

        String token = jwtUtils.generateToken(user.getEmail(), user.getRole().name());
        return new AuthResponse(token, user.getEmail(), user.getRole().name());
    }
}