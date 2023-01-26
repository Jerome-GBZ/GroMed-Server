package com.g2.gromed.composant;

import com.g2.gromed.GromedApplication;
import com.g2.gromed.TestUtils;
import com.g2.gromed.entity.Medicament;
import com.g2.gromed.entity.Presentation;
import com.g2.gromed.model.dto.presentation.Pagination;
import com.g2.gromed.repository.IPresentationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = GromedApplication.class)
class PresentationComposantTest {

    @Autowired
    private IPresentationRepository presentationRepository;

    @Autowired
    private PresentationComposant presentationComposant;

    @Test
    void getPresentations() {
        Medicament medicament = TestUtils.getMedicamentSimple(1);
        Presentation presentation1 = TestUtils.getPresentationMedicamentSimple(1);
        Presentation presentation2 = TestUtils.getPresentationMedicamentSimple(2);
        presentation1.setMedicament(medicament);
        presentation2.setMedicament(medicament);
        List<Presentation> presentations = new ArrayList<>();
        presentations.add(presentation1);
        presentations.add(presentation2);

        presentationRepository.saveAll(presentations);

        Page<Presentation> result = presentationComposant.getPresentations(new Pagination(0, 2));
        Assertions.assertEquals(2, result.getContent().size());
        Assertions.assertEquals(presentation1.getCodeCIP7(), result.getContent().get(0).getCodeCIP7());
        Assertions.assertEquals(presentation1.getMedicament().getCodeCIS(), result.getContent().get(0).getMedicament().getCodeCIS());
        Assertions.assertEquals(presentation2.getCodeCIP7(), result.getContent().get(1).getCodeCIP7());
        Assertions.assertEquals(presentation2.getMedicament().getCodeCIS(), result.getContent().get(1).getMedicament().getCodeCIS());
    }

    @Test
    void getDetailPresentation() {
        Medicament medicament = TestUtils.getMedicamentSimple(1);
        Presentation presentation = TestUtils.getPresentationMedicamentSimple(1);
        presentation.setMedicament(medicament);

        presentationRepository.save(presentation);

        Presentation resultPresentation = presentationComposant.getPresentationByCodeCIP7("CIP7-1");

        Assertions.assertNotNull(presentation);
        Assertions.assertEquals(presentation.getCodeCIP7(), resultPresentation.getCodeCIP7());
        Assertions.assertEquals(presentation.getMedicament().getCodeCIS(), resultPresentation.getMedicament().getCodeCIS());
        Assertions.assertEquals(presentation.getEtatCommercialisation(), resultPresentation.getEtatCommercialisation());
        Assertions.assertEquals(presentation.getPrix(), resultPresentation.getPrix());
    }

}