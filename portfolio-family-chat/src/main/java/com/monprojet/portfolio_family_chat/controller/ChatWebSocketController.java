package com.monprojet.portfolio_family_chat.controller;

import com.monprojet.portfolio_family_chat.dto.MessageDTO;
import com.monprojet.portfolio_family_chat.dto.SendMessageRequest;
import com.monprojet.portfolio_family_chat.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final MessageService messageService;

    // Réceptionne les envois sur /app/chat.sendMessage et rediffuse sur /topic/public
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public MessageDTO sendMessage(@Payload SendMessageRequest request, Authentication authentication) {
        String email = authentication.getName();
        return messageService.sendMessage(email, request);
    }
}