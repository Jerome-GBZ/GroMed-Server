package com.g2.gromed.controller;

import com.g2.gromed.GromedApplication;
import com.g2.gromed.model.dto.commande.PresentationRecapCommandeDTO;
import com.g2.gromed.model.dto.commandetype.CommandeTypeInfo;
import com.g2.gromed.model.dto.utilisateur.UtilisateurDTO;
import com.g2.gromed.service.CommandeTypeService;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = GromedApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
class CommandeTypeControllerTest {
	
	@MockBean
	CommandeTypeService commandeTypeService;
	
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Test
	void getCommandeTypes200() {
		final HttpHeaders headers = new HttpHeaders();
		final Map<String, String> urlParam = new HashMap<>();
		urlParam.put("email", "email");
		urlParam.put("search", "search");
		List<CommandeTypeInfo> commandeTypeInfos = List.of(new CommandeTypeInfo());
		when(commandeTypeService.getCommandeTypes(urlParam.get("email"),urlParam.get("search"))).thenReturn(commandeTypeInfos);
		
		final ResponseEntity<List<CommandeTypeInfo>> response = testRestTemplate.exchange(
				"/commandetype/all?email={email}&search={search}",
				HttpMethod.GET,
				new HttpEntity<>(null, headers),
				new ParameterizedTypeReference<>() {
				},
				urlParam);
		
		ResponseEntity<List<CommandeTypeInfo>> expected = ResponseEntity.ok(commandeTypeInfos);
		assertThat(response).usingRecursiveComparison().ignoringFields("headers").isEqualTo(expected);
	}
	@Test
	void getCommandeTypes404() {
		final HttpHeaders headers = new HttpHeaders();
		final Map<String, String> urlParam = new HashMap<>();
		urlParam.put("email", "email");
		urlParam.put("search", "name");
		when(commandeTypeService.getCommandeTypes(urlParam.get("email"),urlParam.get("search"))).thenReturn(null);
		
		final ResponseEntity<List<CommandeTypeInfo>> response = testRestTemplate.exchange(
				"/commandetype/all?email={email}&search={search}",
				HttpMethod.GET,
				new HttpEntity<>(null, headers),
				new ParameterizedTypeReference<>() {
				},
				urlParam);
		
		ResponseEntity<List<CommandeTypeInfo>> expected = ResponseEntity.notFound().build();
		assertThat(response).usingRecursiveComparison().ignoringFields("headers").isEqualTo(expected);
	}
	@Test
	void getCommandeTypeDetail200() {
		final HttpHeaders headers = new HttpHeaders();
		final Map<String, String> urlParam = new HashMap<>();
		urlParam.put("email", "email");
		urlParam.put("name", "name");
		List<PresentationRecapCommandeDTO> commandeTypeInfos = List.of(new PresentationRecapCommandeDTO());
		when(commandeTypeService.getCommandeTypeDetail(urlParam.get("email"),urlParam.get("name"))).thenReturn(commandeTypeInfos);
		
		final ResponseEntity<List<PresentationRecapCommandeDTO>> response = testRestTemplate.exchange(
				"/commandetype/detail?email={email}&name={name}",
				HttpMethod.GET,
				new HttpEntity<>(null, headers),
				new ParameterizedTypeReference<>() {
				},
				urlParam);
		
		ResponseEntity<List<PresentationRecapCommandeDTO>> expected = ResponseEntity.ok(commandeTypeInfos);
		assertThat(response).usingRecursiveComparison().ignoringFields("headers").isEqualTo(expected);
	}
	@Test
	void getCommandeTypeDetail404() {
		final HttpHeaders headers = new HttpHeaders();
		final Map<String, String> urlParam = new HashMap<>();
		urlParam.put("email", "email");
		urlParam.put("name", "name");
		when(commandeTypeService.getCommandeTypeDetail(urlParam.get("email"),urlParam.get("name"))).thenReturn(null);
		
		final ResponseEntity<List<PresentationRecapCommandeDTO>> response = testRestTemplate.exchange(
				"/commandetype/detail?email={email}&name={name}",
				HttpMethod.GET,
				new HttpEntity<>(null, headers),
				new ParameterizedTypeReference<>() {
				},
				urlParam);
		
		ResponseEntity<List<PresentationRecapCommandeDTO>> expected = ResponseEntity.notFound().build();
		assertThat(response).usingRecursiveComparison().ignoringFields("headers").isEqualTo(expected);
	}
	@Test
	void addCommandeTypeToUserCart200() {
		final HttpHeaders headers = new HttpHeaders();
		final Map<String, String> urlParam = new HashMap<>();
		urlParam.put("email", "email");
		urlParam.put("name", "name");
		UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
		when(commandeTypeService.addCommandeTypeToUserCart(urlParam.get("email"),urlParam.get("name"))).thenReturn(utilisateurDTO);
		
		final ResponseEntity<UtilisateurDTO> response = testRestTemplate.exchange(
				"/commandetype/addtocart?email={email}&name={name}",
				HttpMethod.GET,
				new HttpEntity<>(null, headers),
				new ParameterizedTypeReference<>() {
				},
				urlParam);
		
		ResponseEntity<UtilisateurDTO> expected = ResponseEntity.ok(utilisateurDTO);
		assertThat(response).usingRecursiveComparison().ignoringFields("headers").isEqualTo(expected);
	}
	@Test
	void addCommandeTypeToUserCart404() {
		final HttpHeaders headers = new HttpHeaders();
		final Map<String, String> urlParam = new HashMap<>();
		urlParam.put("email", "email");
		urlParam.put("name", "name");
		when(commandeTypeService.addCommandeTypeToUserCart(urlParam.get("email"),urlParam.get("name"))).thenReturn(null);
		
		final ResponseEntity<List<CommandeTypeInfo>> response = testRestTemplate.exchange(
				"/commandetype/addtocart?email={email}&name={name}",
				HttpMethod.GET,
				new HttpEntity<>(null, headers),
				new ParameterizedTypeReference<>() {
				},
				urlParam);
		
		ResponseEntity<List<CommandeTypeInfo>> expected = ResponseEntity.notFound().build();
		assertThat(response).usingRecursiveComparison().ignoringFields("headers").isEqualTo(expected);
	}
	
	
}
