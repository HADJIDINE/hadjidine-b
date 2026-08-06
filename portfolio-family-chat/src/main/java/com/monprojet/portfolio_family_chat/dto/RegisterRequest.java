package com.monprojet.portfolio_family_chat.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String familyCode; // Le code familial obligatoire à l'inscription
}