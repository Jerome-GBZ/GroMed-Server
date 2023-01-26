package com.g2.gromed.controller;

import com.g2.gromed.GromedApplication;
import com.g2.gromed.TestUtils;
import com.g2.gromed.entity.Presentation;
import com.g2.gromed.mapper.IInfoImportanteMapper;
import com.g2.gromed.mapper.IPresentationMapper;
import com.g2.gromed.model.dto.presentation.InfoImportanteDTO;
import com.g2.gromed.model.dto.presentation.PresentationCardDTO;
import com.g2.gromed.model.dto.presentation.PresentationDetailDTO;
import com.g2.gromed.repository.IPresentationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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

@SpringBootTest(classes = GromedApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class PresentationControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private IPresentationMapper presentationMapper;
    @Autowired
    private IInfoImportanteMapper infoImportanteMapper;
    @Autowired
    private IPresentationRepository presentationRepository;

    @Test
    void getPresentations200() {
        final HttpHeaders headers = new HttpHeaders();
        final Map<String, Integer> urlParam = new HashMap<>();
        urlParam.put("page", 0);
        urlParam.put("size", 2);

        Presentation presentation1 = TestUtils.getPresentationMedicamentSimple(1);
        Presentation presentation2 = TestUtils.getPresentationMedicamentSimple(2);
        presentationRepository.save(presentation1);
        presentationRepository.save(presentation2);

        final ResponseEntity<Page<PresentationCardDTO>> response = testRestTemplate.exchange(
                "/presentation/all?page={page}&size={size}",
                HttpMethod.GET,
                new HttpEntity<>(null, headers),
                new ParameterizedTypeReference<>() {
                },
                urlParam);

        List<PresentationCardDTO> presentationCardDTOList = new ArrayList<>();
        presentationCardDTOList.add(presentationMapper.toPresentationCardDTO(presentation1));
        presentationCardDTOList.add(presentationMapper.toPresentationCardDTO(presentation2));
        final Page<PresentationCardDTO> expectedResponse = new PageImpl<>(presentationCardDTOList, PageRequest.of(urlParam.get("page"), urlParam.get("size")), 1);

        final ResponseEntity<Page<PresentationCardDTO>> expected = ResponseEntity.ok(expectedResponse);

        assertThat(response).usingRecursiveComparison().ignoringFields("headers").isEqualTo(expected);
    }

    @Test
    void getPresentations404() {
        final HttpHeaders headers = new HttpHeaders();
        final Map<String, Integer> urlParam = new HashMap<>();
        urlParam.put("page", 0);
        urlParam.put("size", 1);

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

    @Test
    void getDetailPresentation200() {
        final HttpHeaders headers = new HttpHeaders();
        final Map<String, String> urlParam = new HashMap<>();
        urlParam.put("codeCIP7", "CIP7-1");

        Presentation presentation = TestUtils.getPresentationMedicamentFull(1, 0, 1, 0, 0);
        presentationRepository.save(presentation);

        final ResponseEntity<PresentationDetailDTO> response = testRestTemplate.exchange(
                "/presentation/detail?codeCIP7={codeCIP7}",
                HttpMethod.GET,
                new HttpEntity<>(null, headers),
                new ParameterizedTypeReference<>() {
                },
                urlParam);

        List<InfoImportanteDTO> infoImportanteDTOList = presentation.getMedicament().getInfoImportantes()
                .stream()
                .map(infoImportanteMapper::toInfoImportanteDTO)
                .collect(Collectors.toList());
        PresentationDetailDTO presentationDetailDTO = presentationMapper.toPresentationDetailDTO(presentation, infoImportanteDTOList);

        final ResponseEntity<PresentationDetailDTO> expected = ResponseEntity.ok(presentationDetailDTO);

        assertThat(response).usingRecursiveComparison().ignoringFields("headers").isEqualTo(expected);
    }

    @Test
    void getDetailPresentation404() {
        final HttpHeaders headers = new HttpHeaders();
        final Map<String, String> urlParam = new HashMap<>();
        urlParam.put("codeCIP7", "CIP7-1");

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