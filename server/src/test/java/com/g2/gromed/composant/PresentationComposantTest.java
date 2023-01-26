package com.g2.gromed.composant;

import com.g2.gromed.GromedApplication;
import com.g2.gromed.TestUtils;
import com.g2.gromed.entity.Medicament;
import com.g2.gromed.entity.Presentation;
import com.g2.gromed.model.dto.presentation.Pagination;
import com.g2.gromed.repository.IPresentationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = GromedApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
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
        presentations.add(presentation2);
        presentations.add(presentation1);

        presentationRepository.saveAll(presentations);

        Page<Presentation> expexted = new PageImpl<>(presentations, PageRequest.of(0,2), 1);

        Page<Presentation> result = presentationComposant.getPresentations(new Pagination(0, 2));

        assertThat(result).usingRecursiveComparison().ignoringFields("medicament").isEqualTo(expexted);
    }

    @Test
    void getDetailPresentation() {
        Medicament medicament = TestUtils.getMedicamentSimple(1);
        Presentation presentation = TestUtils.getPresentationMedicamentSimple(1);
        presentation.setMedicament(medicament);

        presentationRepository.save(presentation);

        Presentation resultPresentation = presentationComposant.getPresentationByCodeCIP7("CIP7-1");

        assertThat(resultPresentation).usingRecursiveComparison().ignoringFields("medicament").isEqualTo(presentation);
    }

}