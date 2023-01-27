package com.g2.gromed.controller;

import com.g2.gromed.GromedApplication;
import com.g2.gromed.model.dto.commande.AlerteIndisponibilitePresentationDTO;
import com.g2.gromed.service.CommandeService;
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
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = GromedApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
class CommandeControllerTest {

    @MockBean
    CommandeService commandeService;

    @Autowired
    private TestRestTemplate testRestTemplate;


    @Test
    void checkStockAvailability200() {
        final HttpHeaders headers = new HttpHeaders();
        final Map<String, String> urlParam = new HashMap<>();
        urlParam.put("email", "email");

        Map<String, Integer> alertesMap = new HashMap<>();
        alertesMap.put("CIP7-1", 20);
        alertesMap.put("CIP7-3", 8);

        AlerteIndisponibilitePresentationDTO alerteIndisponibilitePresentationDTO = new AlerteIndisponibilitePresentationDTO(alertesMap);
        when(commandeService.getUnavailablePresentations("email")).thenReturn(alerteIndisponibilitePresentationDTO);

        final ResponseEntity<AlerteIndisponibilitePresentationDTO> response = testRestTemplate.exchange(
                "/commande/panier/disponibilite?email={email}",
                HttpMethod.GET,
                new HttpEntity<>(null, headers),
                new ParameterizedTypeReference<>() {
                },
                urlParam);

        ResponseEntity<AlerteIndisponibilitePresentationDTO> expected = ResponseEntity.ok(alerteIndisponibilitePresentationDTO);
        assertThat(response).usingRecursiveComparison().ignoringFields("headers").isEqualTo(expected);
    }

    @Test
    void checkStockAvailability404() {
        final HttpHeaders headers = new HttpHeaders();
        final Map<String, String> urlParam = new HashMap<>();
        urlParam.put("email", "email");

        when(commandeService.getUnavailablePresentations("email")).thenReturn(null);

        final ResponseEntity<AlerteIndisponibilitePresentationDTO> response = testRestTemplate.exchange(
                "/commande/panier/disponibilite?email={email}",
                HttpMethod.GET,
                new HttpEntity<>(null, headers),
                new ParameterizedTypeReference<>() {
                },
                urlParam);

        ResponseEntity<AlerteIndisponibilitePresentationDTO> expected = ResponseEntity.notFound().build();
        assertThat(response).usingRecursiveComparison().ignoringFields("headers").isEqualTo(expected);
    }
}