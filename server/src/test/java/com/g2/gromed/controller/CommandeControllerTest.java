package com.g2.gromed.controller;

import com.g2.gromed.GromedApplication;
import com.g2.gromed.model.dto.commande.AlerteIndisponibilitePresentationDTO;
import com.g2.gromed.model.dto.commande.CommandeDetailDTO;
import com.g2.gromed.model.dto.commande.LivraisonDTO;
import com.g2.gromed.model.dto.commande.PresentationPanierDTO;
import com.g2.gromed.model.dto.utilisateur.UtilisateurDTO;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    
    @Test
    void addPresentationToCart200(){
        final HttpHeaders headers = new HttpHeaders();
        final Map<String, String> urlParam = new HashMap<>();
        urlParam.put("email", "email");
        urlParam.put("codeCIP7", "cip7");
        urlParam.put("quantite", "1");
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
        utilisateurDTO.setEmail("email");
        utilisateurDTO.setNom("nom");
        utilisateurDTO.setPrenom("prenom");
        utilisateurDTO.setNbMedicamentsPanier(1);
        when(commandeService.addPresentationToUserCart("email", "cip7", 1)).thenReturn(utilisateurDTO);
        final ResponseEntity<UtilisateurDTO> response = testRestTemplate.exchange(
                "/commande/add?email={email}&codeCIP7={codeCIP7}&quantite={quantite}",
                HttpMethod.POST,
                new HttpEntity<>(null, headers),
                new ParameterizedTypeReference<>() {
                },
                urlParam);
    
        ResponseEntity<UtilisateurDTO> expected = ResponseEntity.ok(utilisateurDTO);
        assertThat(response).usingRecursiveComparison().ignoringFields("headers").isEqualTo(expected);
    }
    @Test
    void addPresentationToCart404(){
        final HttpHeaders headers = new HttpHeaders();
        final Map<String, String> urlParam = new HashMap<>();
        urlParam.put("email", "email");
        urlParam.put("codeCIP7", "cip7");
        urlParam.put("quantite", "1");
        when(commandeService.addPresentationToUserCart("email", "cip7", 1)).thenReturn(null);
        final ResponseEntity<UtilisateurDTO> response = testRestTemplate.exchange(
                "/commande/add?email={email}&codeCIP7={codeCIP7}&quantite={quantite}",
                HttpMethod.POST,
                new HttpEntity<>(null, headers),
                new ParameterizedTypeReference<>() {
                },
                urlParam);
        
        ResponseEntity<UtilisateurDTO> expected = ResponseEntity.notFound().build();
        assertThat(response).usingRecursiveComparison().ignoringFields("headers").isEqualTo(expected);
    }
    @Test
    void deletePresentationToCart200(){
        final HttpHeaders headers = new HttpHeaders();
        final Map<String, String> urlParam = new HashMap<>();
        urlParam.put("email", "email");
        urlParam.put("codeCIP7", "cip7");
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
        utilisateurDTO.setEmail("email");
        utilisateurDTO.setNom("nom");
        utilisateurDTO.setPrenom("prenom");
        utilisateurDTO.setNbMedicamentsPanier(0);
        when(commandeService.deletePresentationFromCart("email", "cip7")).thenReturn(utilisateurDTO);
        final ResponseEntity<UtilisateurDTO> response = testRestTemplate.exchange(
                "/commande/delete?email={email}&codeCIP7={codeCIP7}",
                HttpMethod.DELETE,
                new HttpEntity<>(null, headers),
                new ParameterizedTypeReference<>() {
                },
                urlParam);
    
        ResponseEntity<UtilisateurDTO> expected = ResponseEntity.ok(utilisateurDTO);
        assertThat(response).usingRecursiveComparison().ignoringFields("headers").isEqualTo(expected);
    }
    @Test
    void deletePresentationToCart404(){
        final HttpHeaders headers = new HttpHeaders();
        final Map<String, String> urlParam = new HashMap<>();
        urlParam.put("email", "email");
        urlParam.put("codeCIP7", "cip7");
        when(commandeService.deletePresentationFromCart("email", "cip7")).thenReturn(null);
        final ResponseEntity<UtilisateurDTO> response = testRestTemplate.exchange(
                "/commande/delete?email={email}&codeCIP7={codeCIP7}",
                HttpMethod.DELETE,
                new HttpEntity<>(null, headers),
                new ParameterizedTypeReference<>() {
                },
                urlParam);
        
        ResponseEntity<UtilisateurDTO> expected = ResponseEntity.notFound().build();
        assertThat(response).usingRecursiveComparison().ignoringFields("headers").isEqualTo(expected);
    }
    
    @Test
    void getCart200(){
        final HttpHeaders headers = new HttpHeaders();
        final Map<String, String> urlParam = new HashMap<>();
        urlParam.put("email", "email");
        List<PresentationPanierDTO> expectedList = new ArrayList<>();
        expectedList.add(new PresentationPanierDTO());
        when(commandeService.getCart("email")).thenReturn(expectedList);
        final ResponseEntity<List<PresentationPanierDTO>> response = testRestTemplate.exchange(
                "/commande/panier?email={email}",
                HttpMethod.GET,
                new HttpEntity<>(null, headers),
                new ParameterizedTypeReference<>() {
                },
                urlParam);
    
        ResponseEntity<List<PresentationPanierDTO>> expected = ResponseEntity.ok(expectedList);
        assertThat(response).usingRecursiveComparison().ignoringFields("headers").isEqualTo(expected);
    }
    @Test
    void getCart404(){
        final HttpHeaders headers = new HttpHeaders();
        final Map<String, String> urlParam = new HashMap<>();
        urlParam.put("email", "email");
        when(commandeService.getCart("email")).thenReturn(null);
        final ResponseEntity<List<PresentationPanierDTO>> response = testRestTemplate.exchange(
                "/commande/panier?email={email}",
                HttpMethod.GET,
                new HttpEntity<>(null, headers),
                new ParameterizedTypeReference<>() {
                },
                urlParam);
        
        ResponseEntity<List<PresentationPanierDTO>> expected = ResponseEntity.notFound().build();
        assertThat(response).usingRecursiveComparison().ignoringFields("headers").isEqualTo(expected);
    }
    
    @Test
    void validateCart200(){
        final HttpHeaders headers = new HttpHeaders();
        final Map<String, String> urlParam = new HashMap<>();
        urlParam.put("email", "email");
        urlParam.put("saveName", null);
        LivraisonDTO expectedDTO = new LivraisonDTO();
        when(commandeService.validateCart("email","")).thenReturn(expectedDTO);
        final ResponseEntity<LivraisonDTO> response = testRestTemplate.exchange(
                "/commande/validate?email={email}&saveName={saveName}",
                HttpMethod.GET,
                new HttpEntity<>(null, headers),
                new ParameterizedTypeReference<>() {
                },
                urlParam);
    
        ResponseEntity<LivraisonDTO> expected = ResponseEntity.ok(expectedDTO);
        assertThat(response).usingRecursiveComparison().ignoringFields("headers").isEqualTo(expected);
    }
    @Test
    void validateCart404(){
        final HttpHeaders headers = new HttpHeaders();
        final Map<String, String> urlParam = new HashMap<>();
        urlParam.put("email", "email");
        urlParam.put("saveName", null);
        when(commandeService.validateCart("email", "")).thenReturn(null);
        final ResponseEntity<LivraisonDTO> response = testRestTemplate.exchange(
                "/commande/validate?email={email}&saveName={saveName}",
                HttpMethod.GET,
                new HttpEntity<>(null, headers),
                new ParameterizedTypeReference<>() {
                },
                urlParam);
        
        ResponseEntity<LivraisonDTO> expected = ResponseEntity.notFound().build();
        assertThat(response).usingRecursiveComparison().ignoringFields("headers").isEqualTo(expected);
    }
    
    @Test
    void getDetailCommande200(){
        final HttpHeaders headers = new HttpHeaders();
        final Map<String, String> urlParam = new HashMap<>();
        urlParam.put("email", "email");
        urlParam.put("commande", "1");
        CommandeDetailDTO expectedDTO = new CommandeDetailDTO();
        when(commandeService.getDetailCommande("email",1)).thenReturn(expectedDTO);
        final ResponseEntity<CommandeDetailDTO> response = testRestTemplate.exchange(
                "/commande/detail?email={email}&commande={commande}",
                HttpMethod.GET,
                new HttpEntity<>(null, headers),
                new ParameterizedTypeReference<>() {
                },
                urlParam);
        
        ResponseEntity<CommandeDetailDTO> expected = ResponseEntity.ok(expectedDTO);
        assertThat(response).usingRecursiveComparison().ignoringFields("headers").isEqualTo(expected);
    }
    @Test
    void getDetailCommande404(){
        final HttpHeaders headers = new HttpHeaders();
        final Map<String, String> urlParam = new HashMap<>();
        urlParam.put("email", "email");
        urlParam.put("commande", "1");
        when(commandeService.getDetailCommande("email",1)).thenReturn(null);
        final ResponseEntity<CommandeDetailDTO> response = testRestTemplate.exchange(
                "/commande/detail?email={email}&commande={commande}",
                HttpMethod.GET,
                new HttpEntity<>(null, headers),
                new ParameterizedTypeReference<>() {
                },
                urlParam);
        
        ResponseEntity<CommandeDetailDTO> expected = ResponseEntity.notFound().build();
        assertThat(response).usingRecursiveComparison().ignoringFields("headers").isEqualTo(expected);
    }
}