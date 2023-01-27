package com.g2.gromed.service;

import com.g2.gromed.GromedApplication;
import com.g2.gromed.TestUtils;
import com.g2.gromed.composant.CommandeComposant;
import com.g2.gromed.composant.PresentationComposant;
import com.g2.gromed.entity.*;
import com.g2.gromed.model.dto.commande.AlerteIndisponibilitePresentationDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = GromedApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CommandeServiceTest {

    @MockBean
    CommandeComposant commandeComposant;

    @MockBean
    PresentationComposant presentationComposant;

    @Autowired
    CommandeService commandeService;


    @Test
    void getUnavailablePresentationsOK() {

        Medicament medicament = TestUtils.getMedicamentSimple(1);
        Presentation presentation1 = TestUtils.getPresentationWithGivenMedicament(10, medicament);
        Presentation presentation2 = TestUtils.getPresentationWithGivenMedicament(20, medicament);
        Presentation presentation3 = TestUtils.getPresentationWithGivenMedicament(30, medicament);
        Utilisateur utilisateur = TestUtils.getSimpleUtilisateurWithEmail("email");

        Commande commande = TestUtils.getCommandeWith3Products(1, new ArrayList<>(Arrays.asList(presentation1, presentation2, presentation3)));
        commande.setUtilisateur(utilisateur);
        commande.setStatus(StatusCommande.PANIER);

        when(commandeComposant.getCart("email")).thenReturn(commande);
        when(presentationComposant.getPresentationByCodeCIP7(presentation1.getCodeCIP7())).thenReturn(presentation1);
        when(presentationComposant.getPresentationByCodeCIP7(presentation2.getCodeCIP7())).thenReturn(presentation2);
        when(presentationComposant.getPresentationByCodeCIP7(presentation3.getCodeCIP7())).thenReturn(presentation3);

        Map<String, Integer> alertesMap = new HashMap<>();
        alertesMap.put(presentation3.getCodeCIP7(), 100);

        AlerteIndisponibilitePresentationDTO result = commandeService.getUnavailablePresentations(utilisateur.getEmail());
        AlerteIndisponibilitePresentationDTO expected = new AlerteIndisponibilitePresentationDTO(alertesMap);

        Assertions.assertNotNull(result);
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void getUnavailablePresentationsCommandeNull() {
        when(commandeComposant.getCart("email")).thenReturn(null);
        AlerteIndisponibilitePresentationDTO result = commandeService.getUnavailablePresentations("email");
        Assertions.assertNull(result);
    }

    @Test
    void getUnavailablePresentationsNoProductInCart() {
        Commande commande = TestUtils.getCommandeSimple(1);

        when(commandeComposant.getCart("email")).thenReturn(commande);
        AlerteIndisponibilitePresentationDTO result = commandeService.getUnavailablePresentations("email");
        Assertions.assertNull(result);
    }
}