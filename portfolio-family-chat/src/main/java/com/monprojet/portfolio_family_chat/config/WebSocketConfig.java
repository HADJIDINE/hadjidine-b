package com.monprojet.portfolio_family_chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Point d'entrée du Handshake WebSocket pour le client React (SockJS inclus)
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Le préfixe des destinations vers lesquelles les clients publient des messages (@MessageMapping)
        registry.setApplicationDestinationPrefixes("/app");

        // Le préfixe du courtier auquel les clients s'abonnent pour recevoir des messages en temps réel
        registry.enableSimpleBroker("/topic");
    }
}