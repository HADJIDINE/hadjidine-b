package com.monprojet.portfolio_family_chat.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}