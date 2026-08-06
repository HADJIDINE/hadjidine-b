package com.monprojet.portfolio_family_chat.controller;

import com.monprojet.portfolio_family_chat.entity.Message;
import com.monprojet.portfolio_family_chat.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MessageController {

    private final MessageRepository messageRepository;

    @GetMapping
    public ResponseEntity<List<Message>> getMessages(
            @RequestParam(required = false, defaultValue = "") String sender,
            @RequestParam(required = false, defaultValue = "GENERAL") String receiver) {
        try {
            if ("GENERAL".equalsIgnoreCase(receiver) || receiver.isBlank()) {
                return ResponseEntity.ok(messageRepository.findByReceiverOrderByTimestampAsc("GENERAL"));
            }
            return ResponseEntity.ok(messageRepository.findPrivateMessages(sender, receiver));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(List.of());
        }
    }

    @PostMapping
    public ResponseEntity<?> sendMessage(
            @RequestBody Map<String, String> payload,
            Authentication authentication) {
        try {
            // Déterminer l'expéditeur
            String senderEmail = (authentication != null && authentication.getName() != null)
                    ? authentication.getName()
                    : payload.get("sender");

            if (senderEmail == null || senderEmail.isBlank()) {
                senderEmail = "utilisateur@family.com";
            }

            // Déterminer le destinataire (GENERAL par défaut)
            String receiverEmail = payload.getOrDefault("receiver", "GENERAL");
            if (receiverEmail == null || receiverEmail.isBlank()) {
                receiverEmail = "GENERAL";
            }

            // Valider le contenu
            String content = payload.get("content");
            if (content == null || content.isBlank()) {
                return ResponseEntity.badRequest().body("Le message ne peut pas être vide");
            }

            Message message = Message.builder()
                    .sender(senderEmail)
                    .receiver(receiverEmail)
                    .content(content)
                    .timestamp(LocalDateTime.now())
                    .build();

            Message saved = messageRepository.save(message);
            return ResponseEntity.ok(saved);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur serveur BDD: " + e.getMessage());
        }
    }
}