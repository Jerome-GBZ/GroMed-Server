package com.g2.gromed.controller;

import com.g2.gromed.GromedApplication;
import com.g2.gromed.TestUtils;
import com.g2.gromed.entity.Utilisateur;
import com.g2.gromed.mapper.IUtilisateurMapper;
import com.g2.gromed.model.dto.utilisateur.UtilisateurDTO;
import com.g2.gromed.service.UtilisateurService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = GromedApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UtilisateurControllerTest {
	
	@MockBean
	UtilisateurService utilisateurService;
	@Autowired
	IUtilisateurMapper utilisateurMapper;
	@Autowired
	TestRestTemplate testRestTemplate;
	@Test
	void Autenticate200() {
		final HttpHeaders headers = new HttpHeaders();
		final Map<String, String> urlParam = new HashMap<>();
		urlParam.put("email", "test@test.com");
		urlParam.put("motDePasse", "mdp1");
		
		Utilisateur utilisateur = TestUtils.getUtilisateur(1);
		final UtilisateurDTO mockedServiceResponse = utilisateurMapper.toUtilisateurDTO(utilisateur,0);
		
		when(utilisateurService.authenticate(urlParam.get("email"), urlParam.get("motDePasse"))).thenReturn(mockedServiceResponse);
		final ResponseEntity<UtilisateurDTO> response = testRestTemplate.exchange(
				"/utilisateur/connection?email={email}&motDePasse={motDePasse}",
				HttpMethod.POST,
				new HttpEntity<>(null, headers),
				new ParameterizedTypeReference<>() {
				},
				urlParam);
		
		final ResponseEntity<UtilisateurDTO> expected = ResponseEntity.ok(mockedServiceResponse);
		
		assertThat(response).usingRecursiveComparison().ignoringFields("headers").isEqualTo(expected);
	}
	@Test
	void Autenticate404() {
		final HttpHeaders headers = new HttpHeaders();
		final Map<String, String> urlParam = new HashMap<>();
		urlParam.put("email", "test@test.com");
		urlParam.put("motDePasse", "mdp1");
		
		when(utilisateurService.authenticate(urlParam.get("email"), urlParam.get("motDePasse"))).thenReturn(null);
		final ResponseEntity<UtilisateurDTO> response = testRestTemplate.exchange(
				"/utilisateur/connection?email={email}&motDePasse={motDePasse}",
				HttpMethod.POST,
				new HttpEntity<>(null, headers),
				new ParameterizedTypeReference<>() {
				},
				urlParam);
		
		final ResponseEntity<UtilisateurDTO> expected = ResponseEntity.notFound().build();
		
		assertThat(response).usingRecursiveComparison().ignoringFields("headers").isEqualTo(expected);
	}
}
