package com.monprojet.portfolio_family_chat.service;

import com.monprojet.portfolio_family_chat.dto.MessageDTO;
import com.monprojet.portfolio_family_chat.dto.SendMessageRequest;
import com.monprojet.portfolio_family_chat.entity.Message;
import com.monprojet.portfolio_family_chat.entity.User;
import com.monprojet.portfolio_family_chat.repository.MessageRepository;
import com.monprojet.portfolio_family_chat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public List<MessageDTO> getAllMessages() {
        return messageRepository.findAllByOrderByTimestampAsc().stream()
                .map(msg -> {
                    // msg.getSender() renvoie directement l'email (String)
                    String senderEmail = msg.getSender();

                    // On recherche l'utilisateur en BDD pour récupérer son Nom et Prénom
                    User sender = userRepository.findByEmail(senderEmail).orElse(null);

                    return MessageDTO.builder()
                            .id(msg.getId())
                            .senderEmail(senderEmail)
                            .senderNom(sender != null ? sender.getNom() : "")
                            .senderPrenom(sender != null ? sender.getPrenom() : "")
                            .content(msg.getContent())
                            .timestamp(msg.getTimestamp())
                            .build();
                })
                .toList();
    }

    public MessageDTO sendMessage(String userEmail, SendMessageRequest request) {
        User sender = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Message message = Message.builder()
                .sender(sender.getEmail()) // On passe directement l'email (String)
                .receiver("GENERAL")       // Canal par défaut
                .content(request.getContent())
                .timestamp(LocalDateTime.now())
                .build();

        Message saved = messageRepository.save(message);

        return MessageDTO.builder()
                .id(saved.getId())
                .senderEmail(sender.getEmail())
                .senderNom(sender.getNom())
                .senderPrenom(sender.getPrenom())
                .content(saved.getContent())
                .timestamp(saved.getTimestamp())
                .build();
    }
}