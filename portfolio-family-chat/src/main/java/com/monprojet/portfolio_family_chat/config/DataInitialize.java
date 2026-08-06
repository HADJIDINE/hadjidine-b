package com.monprojet.portfolio_family_chat.config;

import com.monprojet.portfolio_family_chat.entity.FamilyCode;
import com.monprojet.portfolio_family_chat.repository.FamilyCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitialize implements CommandLineRunner {

    private final FamilyCodeRepository familyCodeRepository;

    @Override
    public void run(String... args) throws Exception {
        // Vérifie si un code familial existe déjà dans la base
        if (familyCodeRepository.count() == 0) {
            String defaultCode = "FAMILLE2026"; // Code familial par défaut

            FamilyCode familyCode = new FamilyCode();
            familyCode.setCode(defaultCode);
            familyCode.setActive(true);

            familyCodeRepository.save(familyCode);

            log.info("==================================================");
            log.info("Initialisation : Code familial par défaut créé : {}", defaultCode);
            log.info("==================================================");
        } else {
            log.info("Un code familial existe déjà en base de données. Aucune action requise.");
        }
    }
}