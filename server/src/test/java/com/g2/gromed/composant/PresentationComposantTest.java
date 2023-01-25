package com.g2.gromed.composant;

import com.g2.gromed.entity.Medicament;
import com.g2.gromed.entity.Presentation;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class PresentationComposantTest {

    @Test
    void getAllPresentations() {
    }

    private Presentation getPresentation(int n){
        return Presentation.builder()
                .codeCIP7(String.valueOf(n))
                .denomination(String.format("Présentation médicament %d", n))
                .statusAdministratif(String.format("Statut admin médicament %d", n))
                .etatCommercialisation(String.format("Etat comm médicament %d", n))
                .tauxRemboursement((double) n)
                .indicationRemboursement(String.format("Indication remboursement médicament %d", n))
                .prix((double) 10*n)
                .stock(100)
                .build();
    }

    private Medicament getMedicament(int n){
        return Medicament.builder()
                .codeCIS(String.valueOf(n))
                .formePharmaceutique(String.format("Forme pharma médicament %d", n))
                .voiesAdministratifAMM(String.format("Forme pharma médicament %d", n))
                .typeProcedureAMM(String.format("Type procédure médicament %d", n))
                .dateAMM(new Date())
                .etatCommercialisation(String.format("Etat commercialisation %d", n))
                .statusBDM(String.format("Status BDM médicament %d", n))
                .numeroAutorisationEuropeenne(String.format("Numéro auto %d", n))
                .titulaire(String.format("Titulaire médicament %d", n))
                .surveillanceRenforcee("Non")
                .denomination(String.format("Dénomination médicament %d", n))
                .build();
    }
}