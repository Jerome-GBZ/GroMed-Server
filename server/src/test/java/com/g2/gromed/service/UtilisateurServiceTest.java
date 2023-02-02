package com.g2.gromed.service;

import com.g2.gromed.GromedApplication;
import com.g2.gromed.TestUtils;
import com.g2.gromed.composant.UtilisateurComposant;
import com.g2.gromed.entity.Utilisateur;
import com.g2.gromed.mapper.IUtilisateurMapper;
import com.g2.gromed.model.dto.utilisateur.UtilisateurDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = GromedApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
class UtilisateurServiceTest {
	@MockBean
	private UtilisateurComposant utilisateurComposant;
	@Autowired
	private UtilisateurService utilisateurService;
	@Autowired
	private IUtilisateurMapper utilisateurMapper;
	
	@Test
	void Autenticate() {
		Utilisateur utilisateur = TestUtils.getUtilisateur(1);
		when(utilisateurComposant.authenticate(utilisateur.getEmail(), utilisateur.getMotDePasse())).thenReturn(utilisateur);
		UtilisateurDTO exepectedDTO = utilisateurMapper.toUtilisateurDTO(utilisateur,0);
		UtilisateurDTO resultUtilisateur = utilisateurService.authenticate(utilisateur.getEmail(), utilisateur.getMotDePasse());
		assertThat(resultUtilisateur)
				.usingRecursiveComparison()
				.isEqualTo(exepectedDTO);
		
	}
}
