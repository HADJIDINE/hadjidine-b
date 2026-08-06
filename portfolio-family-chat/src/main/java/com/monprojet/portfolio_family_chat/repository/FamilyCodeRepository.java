package com.monprojet.portfolio_family_chat.repository;

import com.monprojet.portfolio_family_chat.entity.FamilyCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FamilyCodeRepository extends JpaRepository<FamilyCode, Long> {

    // Vérifier si un code familial donné existe et est actif
    Optional<FamilyCode> findByCodeAndActiveTrue(String code);

    // Vérifier s'il existe au moins un code en BDD
    boolean existsByCode(String code);
}