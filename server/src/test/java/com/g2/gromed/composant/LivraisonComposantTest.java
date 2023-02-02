package com.g2.gromed.composant;

import com.g2.gromed.GromedApplication;
import com.g2.gromed.TestUtils;
import com.g2.gromed.entity.Commande;
import com.g2.gromed.entity.Livraison;
import com.g2.gromed.entity.StatusCommande;
import com.g2.gromed.repository.ICommandeRepository;
import com.g2.gromed.repository.ILivraisonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = GromedApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
class LivraisonComposantTest {
	@Autowired
	LivraisonComposant livraisonComposant;
	@Autowired
	ILivraisonRepository livraisonRepository;
	@Autowired
	ICommandeRepository commandeRepository;
	
	@Test
	void saveLivraison() {
		Livraison livraison = TestUtils.getLivraison();
		Commande commande = TestUtils.getCommande(1, StatusCommande.EN_COURS);
		commande = commandeRepository.save(commande);
		livraison.setCommande(commande);
		livraison = livraisonComposant.saveLivraison(livraison);
		assertThat(livraisonRepository.findById(livraison.getLivraisonId()))
				.usingRecursiveComparison()
				.ignoringFields("livraisonPresentations", "commande")
				.ignoringFieldsMatchingRegexes(".{0,}$$.{0,}")
				.isEqualTo(livraison);
	}
	@Test
	void getLivraisonByCommande(){
		Livraison livraison = TestUtils.getLivraison();
		Commande commande = TestUtils.getCommande(1, StatusCommande.EN_COURS);
		commande = commandeRepository.save(commande);
		livraison.setCommande(commande);
		livraison = livraisonRepository.save(livraison);
		assertThat(livraisonComposant.getLivraisonsByCommande(commande.getNumeroCommande()))
				.usingRecursiveComparison()
				.ignoringFields("livraisonPresentations", "commande")
				.ignoringFieldsMatchingRegexes(".{0,}$$.{0,}")
				.isEqualTo(livraison);
	}
}
