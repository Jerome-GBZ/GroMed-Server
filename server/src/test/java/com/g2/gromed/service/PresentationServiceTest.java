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
import lombok.extern.java.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@SpringBootTest(classes = GromedApplication.class)
@Log
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

        Assertions.assertEquals(expectedPagePresentationCardDTO.getContent().size(), resultPagePresentationCardDTO.getContent().size());
        Assertions.assertEquals(expectedPagePresentationCardDTO.getContent().get(0).getCodeCIP7(), resultPagePresentationCardDTO.getContent().get(0).getCodeCIP7());
        Assertions.assertEquals(expectedPagePresentationCardDTO.getContent().get(0).isEstGenerique(), resultPagePresentationCardDTO.getContent().get(0).isEstGenerique());
        Assertions.assertEquals(expectedPagePresentationCardDTO.getContent().get(0).getContientInformation(), resultPagePresentationCardDTO.getContent().get(0).getContientInformation());
    }

    @Test
    void getDetailPresentation() {
        Presentation presentation = TestUtils.getPresentationMedicamentFull(1, 2, 2, 2, 2);
        when(presentationComposant.getPresentationByCodeCIP7(presentation.getCodeCIP7())).thenReturn(presentation);

        List<InfoImportante> infoImportanteList = presentation.getMedicament().getInfoImportantes();
        List<InfoImportanteDTO> infoImportanteDTOList = infoImportanteList.stream().map(infoImportanteMapper::toInfoImportanteDTO).collect(Collectors.toList());

        PresentationDetailDTO expectedPresentationDetailDTO = presentationMapper.toPresentationDetailDTO(presentation, infoImportanteDTOList);
        PresentationDetailDTO resultPresentationDetailDTO = presentationService.getDetailPresentation(presentation.getCodeCIP7());

        Assertions.assertEquals(expectedPresentationDetailDTO.getCodeCIP7(), resultPresentationDetailDTO.getCodeCIP7());
        Assertions.assertEquals(expectedPresentationDetailDTO.getInformationsImportantes().size(), resultPresentationDetailDTO.getInformationsImportantes().size());
        Assertions.assertEquals(2, resultPresentationDetailDTO.getInformationsImportantes().size());
        Assertions.assertEquals(expectedPresentationDetailDTO.getInformationsImportantes().get(1).getMessage(), resultPresentationDetailDTO.getInformationsImportantes().get(1).getMessage());
        Assertions.assertEquals("message 1", resultPresentationDetailDTO.getInformationsImportantes().get(1).getMessage());
    }

}