package com.monprojet.portfolio_family_chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    private Long id;
    private String senderEmail;
    private String senderNom;
    private String senderPrenom;
    private String content;
    private LocalDateTime timestamp;
}