package com.g2.gromed.composant;

import com.g2.gromed.GromedApplication;
import com.g2.gromed.TestUtils;
import com.g2.gromed.entity.Commande;
import com.g2.gromed.entity.StatusCommande;
import com.g2.gromed.entity.Utilisateur;
import com.g2.gromed.repository.ICommandeMedicamentRepository;
import com.g2.gromed.repository.ICommandeRepository;
import com.g2.gromed.repository.IPresentationRepository;
import com.g2.gromed.repository.IUtilisateurRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = GromedApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
class CommandeComposantTest {
	@Autowired
	ICommandeRepository commandeRepository;
	@Autowired
	CommandeComposant commandeComposant;
	@Autowired
	IPresentationRepository presentationRepository;
	@Autowired
	ICommandeMedicamentRepository commandeMedicamentRepository;
	@Autowired
	IUtilisateurRepository utilisateurRepository;


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

	/*@Test
	void addToCart() {
		CommandeMedicament commandeMedicament = TestUtils.getCommandeMedicament(1);
		Commande commande = TestUtils.getCommande(1, StatusCommande.PANIER);
		Presentation presentation = TestUtils.getPresentation(1);
		commande = commandeRepository.save(commande);
		presentation = presentationRepository.save(presentation);
		commandeMedicament.setPresentation(presentation);
		commandeMedicament.setCommande(commande);
		commandeComposant.addToCart(commandeMedicament);
		
		assertThat(commandeMedicament)
				.usingRecursiveComparison()
				.ignoringFields("commandeMedicamentId","commande","presentation")
				.ignoringFieldsMatchingRegexes(".{0,}$$.{0,}")
				.isEqualTo(commandeMedicamentRepository.findAll().get(0));
	}
	
	@Test
	void removeFromCart() {
		CommandeMedicament commandeMedicament = TestUtils.getCommandeMedicament(1);
		Commande commande = TestUtils.getCommande(1, StatusCommande.PANIER);
		Presentation presentation = TestUtils.getPresentation(1);
		commande = commandeRepository.save(commande);
		presentation = presentationRepository.save(presentation);
		commandeMedicament.setPresentation(presentation);
		commandeMedicament.setCommande(commande);
		commandeMedicament = commandeMedicamentRepository.save(commandeMedicament);
		commandeComposant.removeFromCart(commandeMedicament);
		
		assertThat(commandeMedicamentRepository.findAll()).isEmpty();
	}*/
	@Test
	void validateCart() {
		Commande commande = TestUtils.getCommande(1, StatusCommande.PANIER);
		Utilisateur utilisateur = TestUtils.getUtilisateur(1);
		utilisateur = utilisateurRepository.save(utilisateur);
		commande.setUtilisateur(utilisateur);
		commande = commandeRepository.save(commande);
		commandeComposant.validateCart(commande);
		assertThat(commande)
				.usingRecursiveComparison()
				.ignoringFields("commandeMedicaments","commandeType","livraisons","dateCommande")
				.ignoringFieldsMatchingRegexes(".{0,}$$.{0,}")
				.isEqualTo(commandeRepository.findAll().get(0));
	}
	
	/*@Test
	void findFirstByNumeroCommandeAndCodeCIP7() {
		CommandeMedicament commandeMedicament = TestUtils.getCommandeMedicament(1);
		Commande commande = TestUtils.getCommande(1, StatusCommande.PANIER);
		Presentation presentation = TestUtils.getPresentation(1);
		commande = commandeRepository.save(commande);
		presentation = presentationRepository.save(presentation);
		commandeMedicament.setCommande(commandeRepository.findAll().get(0));
		commandeMedicament.setPresentation(presentation);
		commandeMedicament = commandeMedicamentRepository.save(commandeMedicament);
		assertThat(commandeComposant.findFirstByNumeroCommandeAndCodeCIP7(commande.getNumeroCommande(),presentation.getCodeCIP7()))
				.usingRecursiveComparison()
				.ignoringFields("commande","presentation")
				.ignoringFieldsMatchingRegexes(".{0,}$$.{0,}")
				.isEqualTo(commandeMedicament);
	}
	
	@Test
	void saveCommandeType(){
		CommandeType commandeType = TestUtils.getCommandeType(1,"commandeType1");
		commandeType = commandeComposant.saveCommandeType(commandeType);
		assertThat(commandeType)
				.usingRecursiveComparison()
				.ignoringFields("commandes")
				.ignoringFieldsMatchingRegexes(".{0,}$$.{0,}")
				.isEqualTo(commandeRepository.findById(1L));
	}*/
	
}
