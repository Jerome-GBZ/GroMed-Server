package com.g2.gromed.composant;

import com.g2.gromed.GromedApplication;
import com.g2.gromed.TestUtils;
import com.g2.gromed.entity.Commande;
import com.g2.gromed.entity.CommandeType;
import com.g2.gromed.entity.StatusCommande;
import com.g2.gromed.entity.Utilisateur;
import com.g2.gromed.repository.ICommandeRepository;
import com.g2.gromed.repository.ICommandeTypeRepository;
import com.g2.gromed.repository.IUtilisateurRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@SpringBootTest(classes = GromedApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
class CommandeTypeComposantTest {
	
	@Autowired
	private ICommandeTypeRepository commandeTypeRepository;
	@Autowired
	private CommandeTypeComposant commandeTypeComposant;
	
	@Autowired
	private IUtilisateurRepository utilisateurRepository;
	@Autowired
	private ICommandeRepository commandeRepository;
	
	@Test
	void getCommandeTypesWithoutResearch(){
		Utilisateur u1 = TestUtils.getUtilisateur(1);
		Commande c1 = TestUtils.getCommande(1, StatusCommande.EN_COURS);
		Commande c2 = TestUtils.getCommande(2, StatusCommande.LIVREE);
		c1= commandeRepository.save(c1);
		c2= commandeRepository.save(c2);
		u1 = utilisateurRepository.save(u1);
		CommandeType commandeType = TestUtils.getCommandeType(1,"salut");
		commandeType.setUtilisateur(u1);
		commandeType.setCommande(c1);
		commandeTypeRepository.save(commandeType);
		CommandeType commandeTyp2 = TestUtils.getCommandeType(2,"test");
		commandeTyp2.setUtilisateur(u1);
		commandeTyp2.setCommande(c2);
		commandeTypeRepository.save(commandeTyp2);
		List<CommandeType> expected = commandeTypeRepository.findAll();
		List<CommandeType> actual = commandeTypeComposant.getCommandeTypes(u1.getEmail(), "");
		assertThat(actual).usingRecursiveComparison().ignoringFields("utilisateur","commande").isEqualTo(expected);
	}
	
	@Test
	void getCommandeTypesWithResearch(){
		Utilisateur u1 = TestUtils.getUtilisateur(1);
		Commande c1 = TestUtils.getCommande(1, StatusCommande.EN_COURS);
		Commande c2 = TestUtils.getCommande(2, StatusCommande.LIVREE);
		c1= commandeRepository.save(c1);
		c2= commandeRepository.save(c2);
		u1 = utilisateurRepository.save(u1);
		CommandeType commandeType = TestUtils.getCommandeType(1,"salut");
		commandeType.setUtilisateur(u1);
		commandeType.setCommande(c1);
		commandeTypeRepository.save(commandeType);
		CommandeType commandeTyp2 = TestUtils.getCommandeType(2,"test");
		commandeTyp2.setUtilisateur(u1);
		commandeTyp2.setCommande(c2);
		commandeTypeRepository.save(commandeTyp2);
		List<CommandeType> expected = List.of(commandeTypeRepository.findAll().get(0));
		List<CommandeType> actual = commandeTypeComposant.getCommandeTypes(u1.getEmail(), "sal");
		assertThat(actual).usingRecursiveComparison().ignoringFields("utilisateur","commande").isEqualTo(expected);
	}
	
	@Test
	void getCommandeType(){
		Utilisateur u1 = TestUtils.getUtilisateur(1);
		Commande c1 = TestUtils.getCommande(1, StatusCommande.EN_COURS);
		c1= commandeRepository.save(c1);
		u1 = utilisateurRepository.save(u1);
		CommandeType commandeType = TestUtils.getCommandeType(1,"salut");
		commandeType.setUtilisateur(u1);
		commandeType.setCommande(c1);
		commandeTypeRepository.save(commandeType);
		CommandeType expected = commandeTypeRepository.findAll().get(0);
		CommandeType actual = commandeTypeComposant.getCommandeType(u1.getEmail(), "salut");
		assertThat(actual).usingRecursiveComparison().ignoringFields("utilisateur","commande").isEqualTo(expected);
	}
}
