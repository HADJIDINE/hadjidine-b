package com.monprojet.portfolio_family_chat.dto;

import com.monprojet.portfolio_family_chat.entity.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private Role role;
    private boolean active;
    private String avatarUrl;
}