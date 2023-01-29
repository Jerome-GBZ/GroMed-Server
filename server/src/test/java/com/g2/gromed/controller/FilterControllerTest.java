package com.g2.gromed.controller;

import com.g2.gromed.GromedApplication;
import com.g2.gromed.model.dto.commande.CommandeDetailDTO;
import com.g2.gromed.model.dto.filtre.FiltreDTO;
import com.g2.gromed.service.FiltreService;
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
class FilterControllerTest {
	@MockBean
	FiltreService filtreService;
	
	@Autowired
	private TestRestTemplate testRestTemplate;
	@Test
	void getFiltres200() {
		final HttpHeaders headers = new HttpHeaders();
		final Map<String, String> urlParam = new HashMap<>();
		when(filtreService.getAllFilters()).thenReturn(null);
		final ResponseEntity<FiltreDTO> response = testRestTemplate.exchange(
				"/filtre/all",
				HttpMethod.GET,
				new HttpEntity<>(null, headers),
				new ParameterizedTypeReference<>() {
				});
		
		ResponseEntity<FiltreDTO> expected = ResponseEntity.notFound().build();
		assertThat(response).usingRecursiveComparison().ignoringFields("headers").isEqualTo(expected);
	}
	@Test
	void getFiltres404() {
		final HttpHeaders headers = new HttpHeaders();
		final Map<String, String> urlParam = new HashMap<>();
		FiltreDTO expectedDTO = new FiltreDTO();
		when(filtreService.getAllFilters()).thenReturn(expectedDTO);
		final ResponseEntity<FiltreDTO> response = testRestTemplate.exchange(
				"/filtre/all",
				HttpMethod.GET,
				new HttpEntity<>(null, headers),
				new ParameterizedTypeReference<>() {
				});
		
		ResponseEntity<FiltreDTO> expected = ResponseEntity.ok(expectedDTO);
		assertThat(response).usingRecursiveComparison().ignoringFields("headers").isEqualTo(expected);
	}
}
