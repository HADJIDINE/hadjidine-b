package com.monprojet.portfolio_family_chat.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "family_codes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FamilyCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private boolean active = true;
}