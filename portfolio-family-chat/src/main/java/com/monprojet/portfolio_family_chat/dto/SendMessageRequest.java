package com.monprojet.portfolio_family_chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageRequest {
    private String receiver; // "GENERAL" ou l'email du destinataire
    private String content;  // Contenu du message
}