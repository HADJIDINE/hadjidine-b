package com.monprojet.portfolio_family_chat.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    // Optionnel : Permet d'associer un avatar par défaut (ex: "M" ou "F")
    private String sexe;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String avatarUrl; // Stocke les images complètes sous forme Data URL / Base64

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder.Default
    private boolean active = true;
}