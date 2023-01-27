package com.g2.gromed.composant;

import com.g2.gromed.GromedApplication;
import com.g2.gromed.TestUtils;
import com.g2.gromed.entity.*;
import com.g2.gromed.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = GromedApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
class CommandeComposantTest {

    @Autowired
    IUtilisateurRepository utilisateurRepository;

    @Autowired
    ICommandeRepository commandeRepository;
    @Autowired
    IPresentationRepository presentationRepository;
    @Autowired
    IMedicamentRepository medicamentRepository;
    @Autowired
    ICommandeMedicamentRepository commandeMedicamentRepository;

    @Autowired
    CommandeComposant commandeComposant;

    @Test
    void getCart() {
        Utilisateur utilisateur = TestUtils.getSimpleUtilisateurWithEmail("email");
        Commande commande = TestUtils.getCommandeSimple(1);
        commande.setUtilisateur(utilisateur);
        commande.setStatus(StatusCommande.PANIER);

        commandeRepository.save(commande);
        utilisateurRepository.save(utilisateur);

        Commande result = commandeComposant.getCart("email");
        Assertions.assertNotNull(result);
        assertThat(result).usingRecursiveComparison().ignoringFields("commandeMedicaments", "livraisons", "utilisateur", "$$_hibernate_attributeInterceptor", "$$_hibernate_collectionTracker", "$$_hibernate_tracker").isEqualTo(commande);
    }
}