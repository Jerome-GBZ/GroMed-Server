package com.g2.gromed.composant;

import com.g2.gromed.GromedApplication;
import com.g2.gromed.TestUtils;
import com.g2.gromed.entity.Presentation;
import com.g2.gromed.repository.IPresentationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatList;

@SpringBootTest(classes = GromedApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
class PresentationComposantTest {

    @Autowired
    private IPresentationRepository presentationRepository;

    @Autowired
    private PresentationComposant presentationComposant;

    @Test
    void getPresentations() {
        Presentation presentation1 = TestUtils.getPresentationMedicamentSimple(1);
        Presentation presentation2 = TestUtils.getPresentationMedicamentSimple(2);
        List<Presentation> presentations = new ArrayList<>();
        presentations.add(presentation1);
        presentations.add(presentation2);

        presentationRepository.save(presentation1);
        presentationRepository.save(presentation2);

        Page<Presentation> expected = new PageImpl<>(presentations, PageRequest.of(0,2), 1);
        Page<Presentation> result = presentationComposant.getPresentations(PageRequest.of(0,2));

        assertThatList(result.getContent()).usingRecursiveComparison().ignoringFields("medicament", "$$_hibernate_attributeInterceptor", "$$_hibernate_tracker").isEqualTo(expected.getContent());
    }

    @Test
    void getDetailPresentation() {
        Presentation presentation = TestUtils.getPresentationMedicamentSimple(1);
        presentationRepository.save(presentation);

        Presentation resultPresentation = presentationComposant.getPresentationByCodeCIP7("CIP7-1");

        Assertions.assertEquals(presentation.getCodeCIP7(), resultPresentation.getCodeCIP7());
        assertThat(resultPresentation).usingRecursiveComparison().ignoringFields("medicament", "$$_hibernate_attributeInterceptor", "$$_hibernate_tracker").isEqualTo(presentation);
    }

}