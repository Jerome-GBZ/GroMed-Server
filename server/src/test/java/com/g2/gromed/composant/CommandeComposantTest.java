package com.g2.gromed.composant;

import com.g2.gromed.GromedApplication;
import com.g2.gromed.TestUtils;
import com.g2.gromed.entity.Commande;
import com.g2.gromed.entity.StatusCommande;
import com.g2.gromed.entity.Utilisateur;
import com.g2.gromed.repository.ICommandeRepository;
import com.g2.gromed.repository.IUtilisateurRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = GromedApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
class CommandeComposantTest {

    @Autowired
    private CommandeComposant commandeComposant;

    @Autowired
    private IUtilisateurRepository utilisateurRepository;

    @Autowired
    private ICommandeRepository commandeRepository;

    @Test
    void getAllByEmail() {
        Utilisateur utilisateur = TestUtils.getUtilisateur(1);
        utilisateurRepository.save(utilisateur);
        Commande commande = TestUtils.getCommande(1, StatusCommande.EN_COURS);
        commande.setUtilisateur(utilisateur);
        Commande commande2 = TestUtils.getCommande(2, StatusCommande.PANIER);
        commande2.setUtilisateur(utilisateur);
        commandeRepository.save(commande2);
        commandeRepository.save(commande);

        List<Commande> expected = new ArrayList<>(List.of(commande));
        List<Commande> result = commandeComposant.getAllByEmail(utilisateur.getEmail());

        //problème sur la date, qui perd les infos sur les h:m:s
        Assertions.assertEquals(expected.get(0).getNumeroCommande(), result.get(0).getNumeroCommande());
        Assertions.assertEquals(expected.get(0).getStatus(), result.get(0).getStatus());
        Assertions.assertEquals(expected.get(0).getTotal(), result.get(0).getTotal());
    }
}