package com.monprojet.portfolio_family_chat.repository;

import com.monprojet.portfolio_family_chat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Trouver un utilisateur par son email (pour l'authentification)
    Optional<User> findByEmail(String email);

    // Vérifier si un email existe déjà lors de l'inscription
    boolean existsByEmail(String email);
}