package com.g2.gromed.service;

import com.g2.gromed.GromedApplication;
import com.g2.gromed.TestUtils;
import com.g2.gromed.composant.PresentationComposant;
import com.g2.gromed.entity.InfoImportante;
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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = GromedApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
class PresentationServiceTest {

    @MockBean
    PresentationComposant presentationComposant;
    @Autowired
    IPresentationMapper presentationMapper;
    @Autowired
    IInfoImportanteMapper infoImportanteMapper;
    @Autowired
    ICompositionMapper compositionMapper;
    @Autowired
    PresentationService presentationService;

   @Test
    void getAllPresentations() {
        Medicament medicament = TestUtils.getMedicament(1);
        Presentation presentation = TestUtils.getPresentation(1);
        presentation.setMedicament(medicament);
        List<Presentation> presentationList = new ArrayList<>();
        presentationList.add(presentation);

        FiltreDTO filtre = new FiltreDTO();

        Page<Presentation> mockPage = new PageImpl<>(presentationList);
        Pageable pagination = PageRequest.of(0, 2);
        when(presentationComposant.getPresentations(pagination, filtre)).thenReturn(mockPage);

        PresentationCardDTO presentationCardDTO = presentationMapper.toPresentationCardDTO(presentation);
        List<PresentationCardDTO> presentationCardDTOList = new ArrayList<>();
        presentationCardDTOList.add(presentationCardDTO);
        Page<PresentationCardDTO> expectedPagePresentationCardDTO = new PageImpl<>(presentationCardDTOList);

        Page<PresentationCardDTO> resultPagePresentationCardDTO = presentationService.getAllPresentations(pagination, filtre);

        assertThat(resultPagePresentationCardDTO).usingRecursiveComparison().isEqualTo(expectedPagePresentationCardDTO);
    }

    @Test
    void getDetailPresentation() {
        Medicament medicament = TestUtils.getMedicament(1);
        Presentation presentation = TestUtils.getPresentation(1);
        presentation.setMedicament(medicament);
        when(presentationComposant.getPresentationByCodeCIP7(presentation.getCodeCIP7())).thenReturn(presentation);

        List<InfoImportante> infoImportanteList = presentation.getMedicament().getInfoImportantes();
        List<InfoImportanteDTO> infoImportanteDTOList = infoImportanteList.stream().map(infoImportanteMapper::toInfoImportanteDTO).collect(Collectors.toList());
        List<CompositionDTO> compositionDTOList = presentation.getMedicament().getCompositions().stream().map(compositionMapper::toCompositionDTO).collect(Collectors.toList());

        PresentationDetailDTO expectedPresentationDetailDTO = presentationMapper.toPresentationDetailDTO(presentation, infoImportanteDTOList, compositionDTOList);
        PresentationDetailDTO resultPresentationDetailDTO = presentationService.getDetailPresentation(presentation.getCodeCIP7());

        assertThat(resultPresentationDetailDTO).usingRecursiveComparison().isEqualTo(expectedPresentationDetailDTO);
    }

    @Test
    void getDetailPresentationNotFound() {
        Presentation presentation = TestUtils.getPresentation(1);
        when(presentationComposant.getPresentationByCodeCIP7(presentation.getCodeCIP7())).thenReturn(null);

        PresentationDetailDTO resultPresentationDetailDTO = presentationService.getDetailPresentation(presentation.getCodeCIP7());
        Assertions.assertNull(resultPresentationDetailDTO);
    }
}