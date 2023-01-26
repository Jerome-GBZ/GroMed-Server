package com.g2.gromed.service;

import com.g2.gromed.GromedApplication;
import com.g2.gromed.TestUtils;
import com.g2.gromed.composant.PresentationComposant;
import com.g2.gromed.entity.InfoImportante;
import com.g2.gromed.entity.Presentation;
import com.g2.gromed.mapper.IInfoImportanteMapper;
import com.g2.gromed.mapper.IPresentationMapper;
import com.g2.gromed.model.dto.presentation.InfoImportanteDTO;
import com.g2.gromed.model.dto.presentation.Pagination;
import com.g2.gromed.model.dto.presentation.PresentationCardDTO;
import com.g2.gromed.model.dto.presentation.PresentationDetailDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

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
    PresentationService presentationService;

    @Test
    void getAllPresentations() {
        Presentation presentation = TestUtils.getPresentationMedicamentSimple(1);
        List<Presentation> presentationList = new ArrayList<>();
        presentationList.add(presentation);

        Page<Presentation> mockPage = new PageImpl<>(presentationList);
        Pagination pagination = new Pagination(0, 2);
        when(presentationComposant.getPresentations(pagination)).thenReturn(mockPage);

        PresentationCardDTO presentationCardDTO = presentationMapper.toPresentationCardDTO(presentation);
        List<PresentationCardDTO> presentationCardDTOList = new ArrayList<>();
        presentationCardDTOList.add(presentationCardDTO);
        Page<PresentationCardDTO> expectedPagePresentationCardDTO = new PageImpl<>(presentationCardDTOList);

        Page<PresentationCardDTO> resultPagePresentationCardDTO = presentationService.getAllPresentations(pagination);

        assertThat(resultPagePresentationCardDTO).usingRecursiveComparison().isEqualTo(expectedPagePresentationCardDTO);
    }


    @Test
    void getDetailPresentation() {
        Presentation presentation = TestUtils.getPresentationMedicamentFull(1, 2, 2, 2, 2);
        when(presentationComposant.getPresentationByCodeCIP7(presentation.getCodeCIP7())).thenReturn(presentation);

        List<InfoImportante> infoImportanteList = presentation.getMedicament().getInfoImportantes();
        List<InfoImportanteDTO> infoImportanteDTOList = infoImportanteList.stream().map(infoImportanteMapper::toInfoImportanteDTO).collect(Collectors.toList());

        PresentationDetailDTO expectedPresentationDetailDTO = presentationMapper.toPresentationDetailDTO(presentation, infoImportanteDTOList);
        PresentationDetailDTO resultPresentationDetailDTO = presentationService.getDetailPresentation(presentation.getCodeCIP7());

        assertThat(resultPresentationDetailDTO).usingRecursiveComparison().isEqualTo(expectedPresentationDetailDTO);
    }

}