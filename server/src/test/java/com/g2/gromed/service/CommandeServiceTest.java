package com.g2.gromed.service;

import com.g2.gromed.GromedApplication;
import com.g2.gromed.TestUtils;
import com.g2.gromed.composant.CommandeComposant;
import com.g2.gromed.composant.PresentationComposant;
import com.g2.gromed.entity.*;
import com.g2.gromed.mapper.ICommandeMapper;
import com.g2.gromed.model.dto.commande.AlerteIndisponibilitePresentationDTO;
import com.g2.gromed.model.dto.commande.CommandeDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.util.*;

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

    @Autowired
    ICommandeMapper commandeMapper;


    @Test
    void getUnavailablePresentationsOK() {

        Medicament medicament = TestUtils.getMedicament(1);

        Presentation presentation1 = TestUtils.getPresentation(10);
        presentation1.setMedicament(medicament);
        Presentation presentation2 = TestUtils.getPresentation(11);
        presentation2.setMedicament(medicament);

        Utilisateur utilisateur = TestUtils.getUtilisateur(1);

        Commande commande = TestUtils.getCommande(1, StatusCommande.PANIER);
        commande.setUtilisateur(utilisateur);

        CommandeMedicament commandeMedicament1 = TestUtils.getCommandeMedicament(5);
        commandeMedicament1.setPresentation(presentation1);
        CommandeMedicament commandeMedicament2 = TestUtils.getCommandeMedicament(20);
        commandeMedicament2.setPresentation(presentation2);
        List<CommandeMedicament> commandeMedicamentList = new ArrayList<>(Arrays.asList(commandeMedicament2, commandeMedicament1));
        commande.setCommandeMedicaments(commandeMedicamentList);


        when(commandeComposant.getCart(utilisateur.getEmail())).thenReturn(commande);
        when(presentationComposant.getPresentationByCodeCIP7(presentation1.getCodeCIP7())).thenReturn(presentation1);
        when(presentationComposant.getPresentationByCodeCIP7(presentation2.getCodeCIP7())).thenReturn(presentation2);

        Map<String, Integer> alertesMap = new HashMap<>();
        alertesMap.put(presentation2.getCodeCIP7(), 100);

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
        Commande commande = TestUtils.getCommande(1, StatusCommande.PANIER);

        when(commandeComposant.getCart("email")).thenReturn(commande);
        AlerteIndisponibilitePresentationDTO result = commandeService.getUnavailablePresentations("email");
        Assertions.assertNull(result);
    }

    @Test
    void getAllCommande(){
        Commande commande1 = TestUtils.getCommande(1, StatusCommande.EN_COURS);
        Commande commande3 = TestUtils.getCommande(3, StatusCommande.LIVREE);
        List<Commande> commandes = new ArrayList<>(Arrays.asList(commande1, commande3));

        when(commandeComposant.getAllByEmail("email")).thenReturn(commandes);

        List<CommandeDTO> expected = commandes.stream().map(commande -> commandeMapper.commandeToCommandeDTO(commande, true)).toList();
        List<CommandeDTO> result = commandeService.getAllCommande("email");

        Assertions.assertEquals(expected.size(), result.size());
        Assertions.assertEquals(expected.get(0).getNumeroCommande(), result.get(0).getNumeroCommande());
        Assertions.assertEquals(expected.get(1).getNumeroCommande(), result.get(1).getNumeroCommande());
        Assertions.assertEquals(expected.get(0).getTotal(), result.get(0).getTotal());
        Assertions.assertEquals(expected.get(1).getTotal(), result.get(1).getTotal());

    }

}