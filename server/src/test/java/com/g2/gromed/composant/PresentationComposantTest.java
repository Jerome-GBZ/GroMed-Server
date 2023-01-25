package com.g2.gromed.composant;

import com.g2.gromed.GromedApplication;
import com.g2.gromed.entity.Medicament;
import com.g2.gromed.entity.Presentation;
import com.g2.gromed.model.dto.presentation.Pagination;
import com.g2.gromed.repository.IPresentationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest(classes = GromedApplication.class)
class PresentationComposantTest {

    @Autowired
    private IPresentationRepository presentationRepository;

    @Autowired
    private PresentationComposant presentationComposant;

    @Test
    void getAllPresentations() {
        Medicament medicament = getMedicament();
        Presentation presentation1 = getPresentation(1);
        Presentation presentation2 = getPresentation(2);
        presentation1.setMedicament(medicament);
        presentation2.setMedicament(medicament);
        List<Presentation> presentations = new ArrayList<>();
        presentations.add(presentation1);
        presentations.add(presentation2);

        presentationRepository.saveAll(presentations);

        Page<Presentation> result = presentationComposant.getPresentations(new Pagination(0, 2));
        Assertions.assertEquals(2, result.getContent().size());
        Assertions.assertEquals("CIP7-1", result.getContent().get(0).getCodeCIP7());
        Assertions.assertEquals("CIS-1", result.getContent().get(0).getMedicament().getCodeCIS());
        Assertions.assertEquals("CIP7-2", result.getContent().get(1).getCodeCIP7());
        Assertions.assertEquals("CIS-1", result.getContent().get(1).getMedicament().getCodeCIS());
    }

    private Presentation getPresentation(int n){
        return Presentation.builder()
                .codeCIP7(String.format("CIP7-%d", n))
                .denomination(String.format("Présentation médicament %d", n))
                .statusAdministratif(String.format("Statut admin médicament %d", n))
                .etatCommercialisation(String.format("Etat comm médicament %d", n))
                .tauxRemboursement((double) n)
                .indicationRemboursement(String.format("Indication remboursement médicament %d", n))
                .prix((double) 10*n)
                .stock(100)
                .build();
    }

    private Medicament getMedicament(){
        return Medicament.builder()
                .codeCIS(String.format("CIS-%d", 1))
                .formePharmaceutique(String.format("Forme pharma médicament %d", 1))
                .voiesAdministratifAMM(String.format("Forme pharma médicament %d", 1))
                .typeProcedureAMM(String.format("Type procédure médicament %d", 1))
                .dateAMM(new Date())
                .etatCommercialisation(String.format("Etat commercialisation %d", 1))
                .statusBDM(String.format("Status BDM médicament %d", 1))
                .numeroAutorisationEuropeenne(String.format("Numéro auto %d", 1))
                .titulaire(String.format("Titulaire médicament %d", 1))
                .surveillanceRenforcee("Non")
                .denomination(String.format("Dénomination médicament %d", 1))
                .build();
    }
}