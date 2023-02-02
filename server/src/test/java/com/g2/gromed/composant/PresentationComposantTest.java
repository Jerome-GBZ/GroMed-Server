package com.g2.gromed.composant;

import com.g2.gromed.GromedApplication;
import com.g2.gromed.TestUtils;
import com.g2.gromed.entity.Composition;
import com.g2.gromed.entity.GroupeGenerique;
import com.g2.gromed.entity.Medicament;
import com.g2.gromed.entity.Presentation;
import com.g2.gromed.model.dto.filtre.FiltreDTO;
import com.g2.gromed.repository.ICompositionRepository;
import com.g2.gromed.repository.IGroupeGeneriqueRepository;
import com.g2.gromed.repository.IMedicamentRepository;
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
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThatList;

@SpringBootTest(classes = GromedApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
class PresentationComposantTest {

    @Autowired
    private IPresentationRepository presentationRepository;

    @Autowired
    private ICompositionRepository compositionRepository;

    @Autowired
    private IGroupeGeneriqueRepository groupeGeneriqueRepository;

    @Autowired
    private IMedicamentRepository medicamentRepository;

    @Autowired
    private PresentationComposant presentationComposant;

    @Test
    void getPresentationsGenerique() {
        //res : presentation13 avec medicament2 composition1 groupeGenerique1-2 stock100 titulaire-boirons

        Composition composition1 = TestUtils.getComposition(1);
        Composition composition2 = TestUtils.getComposition(2);
        composition1.setDenominationSubstance("paracetamol");

        compositionRepository.save(composition1);
        compositionRepository.save(composition2);

        GroupeGenerique groupeGenerique1 = TestUtils.getGroupeGenerique(1);
        GroupeGenerique groupeGenerique2 = TestUtils.getGroupeGenerique(2);

        groupeGeneriqueRepository.save(groupeGenerique1);
        groupeGeneriqueRepository.save(groupeGenerique2);

        Medicament medicament1 = TestUtils.getMedicament(1);
        Medicament medicament2 = TestUtils.getMedicament(2);
        medicament2.setTitulaire("boirons");

        medicament2.getGroupeGeneriques().add(groupeGenerique1);
        medicament2.getGroupeGeneriques().add(groupeGenerique2);

        medicament2.getCompositions().add(composition1);
        medicament1.getCompositions().add(composition2);

        medicamentRepository.save(medicament1);
        medicamentRepository.save(medicament2);

        Presentation presentation1 = TestUtils.getPresentation(1);
        presentation1.setStock(0);
        Presentation presentation13 = TestUtils.getPresentation(13);
        Presentation presentation2 = TestUtils.getPresentation(2);
        Presentation presentation21 = TestUtils.getPresentation(21);

        presentation1.setMedicament(medicament1);
        presentation13.setMedicament(medicament2);
        presentation2.setMedicament(medicament2);
        presentation21.setMedicament(medicament1);

        presentationRepository.save(presentation1);
        presentationRepository.save(presentation13);
        presentationRepository.save(presentation2);
        presentationRepository.save(presentation21);


        FiltreDTO filtreDTO = new FiltreDTO();
        filtreDTO.setPresentationName("2");
        filtreDTO.setAvailable(true);
        filtreDTO.setGenerique(true);
        filtreDTO.setOriginal(false);
        filtreDTO.setTitulaires(List.of("boirons"));
        filtreDTO.setSubstancesDenomitations(List.of("paracetamol"));

        Page<Presentation> expected = new PageImpl<>(List.of(presentation13), PageRequest.of(0, 2), 1);
        Page<Presentation> result = presentationComposant.getPresentations(PageRequest.of(0, 2), filtreDTO);

        assertThatList(result.getContent())
                .usingRecursiveComparison()
                .ignoringFieldsMatchingRegexes(".{0,}$$.{0,}")
                .isEqualTo(expected.getContent());
    }

    @Test
    void getPresentationsOriginal() {
        Composition composition1 = TestUtils.getComposition(1);
        Composition composition2 = TestUtils.getComposition(2);
        composition1.setDenominationSubstance("paracetamol");

        compositionRepository.save(composition1);
        compositionRepository.save(composition2);

        GroupeGenerique groupeGenerique1 = TestUtils.getGroupeGenerique(1);
        GroupeGenerique groupeGenerique2 = TestUtils.getGroupeGenerique(2);

        groupeGeneriqueRepository.save(groupeGenerique1);
        groupeGeneriqueRepository.save(groupeGenerique2);

        Medicament medicament1 = TestUtils.getMedicament(1);
        Medicament medicament2 = TestUtils.getMedicament(2);
        medicament2.setTitulaire("boirons");

        medicament2.getGroupeGeneriques().add(groupeGenerique1);
        medicament2.getGroupeGeneriques().add(groupeGenerique2);

        medicament2.getCompositions().add(composition1);
        medicament1.getCompositions().add(composition2);

        medicamentRepository.save(medicament1);
        medicamentRepository.save(medicament2);

        Presentation presentation1 = TestUtils.getPresentation(1);
        presentation1.setStock(0);
        Presentation presentation13 = TestUtils.getPresentation(13);
        Presentation presentation2 = TestUtils.getPresentation(2);
        Presentation presentation21 = TestUtils.getPresentation(21);

        presentation1.setMedicament(medicament1);
        presentation13.setMedicament(medicament2);
        presentation2.setMedicament(medicament2);
        presentation21.setMedicament(medicament1);

        presentationRepository.save(presentation1);
        presentationRepository.save(presentation13);
        presentationRepository.save(presentation2);
        presentationRepository.save(presentation21);


        FiltreDTO filtreDTO = new FiltreDTO();
        filtreDTO.setPresentationName("1");
        filtreDTO.setAvailable(true);
        filtreDTO.setGenerique(false);
        filtreDTO.setOriginal(true);
        filtreDTO.setTitulaires(new ArrayList<>());
        filtreDTO.setSubstancesDenomitations(new ArrayList<>());

        Page<Presentation> expected = new PageImpl<>(List.of(presentation21), PageRequest.of(0, 2), 1);
        Page<Presentation> result = presentationComposant.getPresentations(PageRequest.of(0, 2), filtreDTO);

        assertThatList(result.getContent())
                .usingRecursiveComparison()
                .ignoringFieldsMatchingRegexes(".{0,}$$.{0,}")
                .isEqualTo(expected.getContent());
    }

    @Test
    void getPresentationsNoFilterOriginal() {
        Composition composition1 = TestUtils.getComposition(1);
        Composition composition2 = TestUtils.getComposition(2);
        composition1.setDenominationSubstance("paracetamol");

        compositionRepository.save(composition1);
        compositionRepository.save(composition2);

        GroupeGenerique groupeGenerique1 = TestUtils.getGroupeGenerique(1);
        GroupeGenerique groupeGenerique2 = TestUtils.getGroupeGenerique(2);

        groupeGeneriqueRepository.save(groupeGenerique1);
        groupeGeneriqueRepository.save(groupeGenerique2);

        Medicament medicament1 = TestUtils.getMedicament(1);
        Medicament medicament2 = TestUtils.getMedicament(2);
        medicament2.setTitulaire("boirons");

        medicament2.getGroupeGeneriques().add(groupeGenerique1);
        medicament2.getGroupeGeneriques().add(groupeGenerique2);

        medicament2.getCompositions().add(composition1);
        medicament1.getCompositions().add(composition2);

        medicamentRepository.save(medicament1);
        medicamentRepository.save(medicament2);

        Presentation presentation1 = TestUtils.getPresentation(1);
        presentation1.setStock(0);
        Presentation presentation13 = TestUtils.getPresentation(13);
        Presentation presentation2 = TestUtils.getPresentation(2);
        Presentation presentation21 = TestUtils.getPresentation(21);

        presentation1.setMedicament(medicament1);
        presentation13.setMedicament(medicament2);
        presentation2.setMedicament(medicament2);
        presentation21.setMedicament(medicament1);

        presentationRepository.save(presentation1);
        presentationRepository.save(presentation13);
        presentationRepository.save(presentation2);
        presentationRepository.save(presentation21);

        FiltreDTO filtreDTO = new FiltreDTO();
        filtreDTO.setPresentationName("");
        filtreDTO.setAvailable(false);
        filtreDTO.setGenerique(false);
        filtreDTO.setOriginal(true);
        filtreDTO.setTitulaires(null);
        filtreDTO.setSubstancesDenomitations(null);

        Page<Presentation> expected = new PageImpl<>(List.of(presentation21), PageRequest.of(0, 2), 1);
        Page<Presentation> result = presentationComposant.getPresentations(PageRequest.of(0, 2), filtreDTO);

        assertThatList(result.getContent())
                .usingRecursiveComparison()
                .ignoringFieldsMatchingRegexes(".{0,}$$.{0,}")
                .isEqualTo(expected.getContent());
    }


    @Test
    void getDetailPresentation() {
        Presentation presentation = TestUtils.getPresentation(1);
        presentationRepository.save(presentation);

        Presentation resultPresentation = presentationComposant.getPresentationByCodeCIP7("CIP7-1");

        Assertions.assertEquals(presentation.getCodeCIP7(), resultPresentation.getCodeCIP7());
        assertThat(resultPresentation)
                .usingRecursiveComparison()
                .ignoringFields("medicament")
                .ignoringFieldsMatchingRegexes(".{0,}$$.{0,}")
                .isEqualTo(presentation);
    }
    @Test
    void updatePresentation(){
        Presentation presentation = TestUtils.getPresentation(1);
        presentationRepository.save(presentation);
        presentation.setStock(10);
        presentationComposant.updatePresentation(presentation);

        Presentation resultPresentation = presentationRepository.findFirstByCodeCIP7("CIP7-1");

        Assertions.assertEquals(presentation.getCodeCIP7(), resultPresentation.getCodeCIP7());
        assertThat(resultPresentation)
                .usingRecursiveComparison()
                .ignoringFields("medicament")
                .ignoringFieldsMatchingRegexes(".{0,}$$.{0,}")
                .isEqualTo(presentation);
    }

}