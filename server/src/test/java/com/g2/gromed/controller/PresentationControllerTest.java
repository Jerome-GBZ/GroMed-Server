package com.g2.gromed.controller;

import com.g2.gromed.GromedApplication;
import com.g2.gromed.TestUtils;
import com.g2.gromed.entity.Medicament;
import com.g2.gromed.entity.Presentation;
import com.g2.gromed.mapper.ICompositionMapper;
import com.g2.gromed.mapper.IInfoImportanteMapper;
import com.g2.gromed.mapper.IPresentationMapper;
import com.g2.gromed.model.dto.filtre.FiltreDTO;
import com.g2.gromed.model.dto.presentation.CompositionDTO;
import com.g2.gromed.model.dto.presentation.InfoImportanteDTO;
import com.g2.gromed.model.dto.presentation.PresentationCardDTO;
import com.g2.gromed.model.dto.presentation.PresentationDetailDTO;
import com.g2.gromed.service.PresentationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = GromedApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class PresentationControllerTest {

    @MockBean
    PresentationService presentationService;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private IPresentationMapper presentationMapper;

    @Autowired
    private IInfoImportanteMapper infoImportanteMapper;
    @Autowired
    private ICompositionMapper compositionMapper;


    @Test
    void getPresentations200() {
        final HttpHeaders headers = new HttpHeaders();

        FiltreDTO filtre = new FiltreDTO();
        filtre.setPresentationName("A");
        filtre.setGenerique(false);
        filtre.setOriginal(true);
        filtre.setAvailable(true);
        filtre.setTitulaires(List.of());
        filtre.setSubstancesDenomitations(List.of());

        Medicament medicament = TestUtils.getMedicament(10);
        Presentation presentation1 = TestUtils.getPresentation(1);
        presentation1.setMedicament(medicament);
        Presentation presentation2 = TestUtils.getPresentation(2);
        presentation2.setMedicament(medicament);

        List<PresentationCardDTO> presentationCardDTOList = new ArrayList<>();
        presentationCardDTOList.add(presentationMapper.toPresentationCardDTO(presentation1));
        presentationCardDTOList.add(presentationMapper.toPresentationCardDTO(presentation2));
        final Page<PresentationCardDTO> mockedServiceResponse = new PageImpl<>(presentationCardDTOList, PageRequest.of(0, 2), 2);

        Assertions.assertNotNull(mockedServiceResponse);
        when(presentationService.getAllPresentations(any(Pageable.class), any(FiltreDTO.class))).thenReturn(mockedServiceResponse);
        //doReturn(mockedServiceResponse).when(presentationService).getAllPresentations(any(Pageable.class), eq(filtre));

        final ResponseEntity<Page<PresentationCardDTO>> response = testRestTemplate.exchange(
                "/presentation/all?page=0&size=100&presentationName=A&generique=false&original=true&available=true&titulaires=&substancesDenomitations=",
                HttpMethod.GET,
                new HttpEntity<>(null, headers),
                new ParameterizedTypeReference<>() {
                });


        final ResponseEntity<Page<PresentationCardDTO>> expected = ResponseEntity.ok(mockedServiceResponse);

        assertThat(response).usingRecursiveComparison().ignoringFields("headers").isEqualTo(expected);
    }


/*

    @Test
    void getPresentations404() {
        final HttpHeaders headers = new HttpHeaders();
        final Map<String, Integer> urlParam = new HashMap<>();
        urlParam.put("page", 0);
        urlParam.put("size", 1);

        when(presentationService.getAllPresentations(PageRequest.of(0, 1))).thenReturn(new PageImpl<>(new ArrayList<>(), PageRequest.of(urlParam.get("page"), urlParam.get("size")), 1));
        final ResponseEntity<Page<PresentationCardDTO>> response = testRestTemplate.exchange(
                "/presentation/all?page={page}&size={size}",
                HttpMethod.GET,
                new HttpEntity<>(null, headers),
                new ParameterizedTypeReference<>() {
                },
                urlParam);

        final ResponseEntity<Page<PresentationCardDTO>> expected = ResponseEntity.notFound().build();

        assertThat(response).usingRecursiveComparison().ignoringFields("headers").isEqualTo(expected);
    }
*/

    @Test
    void getDetailPresentation200() {
        final HttpHeaders headers = new HttpHeaders();
        final Map<String, String> urlParam = new HashMap<>();
        urlParam.put("codeCIP7", "CIP7-1");

        Presentation presentation = TestUtils.getPresentation(1);
        Medicament medicament = TestUtils.getMedicament(10);
        presentation.setMedicament(medicament);

        List<InfoImportanteDTO> infoImportanteDTOList = presentation.getMedicament().getInfoImportantes()
                .stream()
                .map(infoImportanteMapper::toInfoImportanteDTO)
                .collect(Collectors.toList());
        List<CompositionDTO> compositionDTOList = presentation.getMedicament().getCompositions()
                .stream()
                .map(compositionMapper::toCompositionDTO)
                .collect(Collectors.toList());
        PresentationDetailDTO mockedServiceResponse = presentationMapper.toPresentationDetailDTO(presentation, infoImportanteDTOList, compositionDTOList);

        when(presentationService.getDetailPresentation("CIP7-1")).thenReturn(mockedServiceResponse);

        final ResponseEntity<PresentationDetailDTO> response = testRestTemplate.exchange(
                "/presentation/detail?codeCIP7={codeCIP7}",
                HttpMethod.GET,
                new HttpEntity<>(null, headers),
                new ParameterizedTypeReference<>() {
                },
                urlParam);

        final ResponseEntity<PresentationDetailDTO> expected = ResponseEntity.ok(mockedServiceResponse);

        assertThat(response).usingRecursiveComparison().ignoringFields("headers", "medicament").isEqualTo(expected);
    }

    @Test
    void getDetailPresentation404() {
        final HttpHeaders headers = new HttpHeaders();
        final Map<String, String> urlParam = new HashMap<>();
        urlParam.put("codeCIP7", "CIP7-1");

        when(presentationService.getDetailPresentation("CIP-7")).thenReturn(null);

        final ResponseEntity<PresentationDetailDTO> response = testRestTemplate.exchange(
                "/presentation/detail?codeCIP7={codeCIP7}",
                HttpMethod.GET,
                new HttpEntity<>(null, headers),
                new ParameterizedTypeReference<>() {
                },
                urlParam);

        final ResponseEntity<PresentationDetailDTO> expected = ResponseEntity.notFound().build();
        assertThat(response).usingRecursiveComparison().ignoringFields("headers").isEqualTo(expected);
    }
}