package com.monprojet.portfolio_family_chat.repository;

import com.monprojet.portfolio_family_chat.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    // --- AJOUT OBLIGATOIRE : Récupérer tous les messages triés par date ---
    List<Message> findAllByOrderByTimestampAsc();

    // Récupérer les messages du groupe Général
    List<Message> findByReceiverOrderByTimestampAsc(String receiver);

    // Récupérer la conversation privée entre deux utilisateurs
    @Query("SELECT m FROM Message m WHERE " +
            "(m.sender = :user1 AND m.receiver = :user2) OR " +
            "(m.sender = :user2 AND m.receiver = :user1) " +
            "ORDER BY m.timestamp ASC")
    List<Message> findPrivateMessages(@Param("user1") String user1, @Param("user2") String user2);
}