package com.g2.gromed.composant;

import com.g2.gromed.GromedApplication;
import com.g2.gromed.TestUtils;
import com.g2.gromed.entity.Utilisateur;
import com.g2.gromed.repository.IUtilisateurRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = GromedApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
class UtilisateurComposantTest {
	@Autowired
	private UtilisateurComposant utilisateurComposant;
	@Autowired
	 private IUtilisateurRepository utilisateurRepository;
	

	@Test
	void Autenticate() throws ParseException {
		Utilisateur utilisateur = TestUtils.getUtilisateur(1);
		utilisateur = utilisateurRepository.save(utilisateur);
		
		Utilisateur resultUtilisateur = utilisateurComposant.authenticate(utilisateur.getEmail(), utilisateur.getMotDePasse());
		assertThat(resultUtilisateur)
				.usingRecursiveComparison()
				.ignoringFields("commandes","commandeTypes", "etablissement")
				.ignoringFieldsMatchingRegexes(".{0,}$$.{0,}")
				.isEqualTo(utilisateur);
	}
	
	@Test
	void getUserByEmail() throws ParseException {
		Utilisateur utilisateur = TestUtils.getUtilisateur(1);
		utilisateur = utilisateurRepository.save(utilisateur);
		
		Utilisateur resultUtilisateur = utilisateurComposant.getUserByEmail(utilisateur.getEmail());
		assertThat(resultUtilisateur)
				.usingRecursiveComparison()
				.ignoringFields("commandes","commandeTypes", "etablissement")
				.ignoringFieldsMatchingRegexes(".{0,}$$.{0,}")
				.isEqualTo(utilisateur);
	}
	
	
}
