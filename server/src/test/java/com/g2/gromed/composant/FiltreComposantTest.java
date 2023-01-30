package com.g2.gromed.composant;

import com.g2.gromed.GromedApplication;
import com.g2.gromed.TestUtils;
import com.g2.gromed.entity.Composition;
import com.g2.gromed.entity.Medicament;
import com.g2.gromed.repository.ICompositionRepository;
import com.g2.gromed.repository.IMedicamentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = GromedApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
class FiltreComposantTest {
	@Autowired
	FiltreComposant filtreComposant;
	
	@Autowired
	IMedicamentRepository medicamentRepository;
	
	@Autowired
	ICompositionRepository compositionRepository;
	
	@Test
	void getAllSubstancesDenomination() {
		Composition composition = TestUtils.getComposition(1);
		compositionRepository.save(composition);
		Composition composition2 = TestUtils.getComposition(2);
		compositionRepository.save(composition2);
		List<String> expected = List.of("denomination 1", "denomination 2");
		List<String> substancesDenomination = filtreComposant.getAllSubstancesDenomination();
		assertThat(substancesDenomination)
				.usingRecursiveComparison()
				.isEqualTo(expected);
	}
	
	@Test
	void getAllTitulaires(){
		Medicament medicament = TestUtils.getMedicament(1);
		medicamentRepository.save(medicament);
		Medicament medicament2 = TestUtils.getMedicament(2);
		medicamentRepository.save(medicament2);
		List<String> expected = List.of("Titulaire médicament 1", "Titulaire médicament 2");
		List<String> titulaires = filtreComposant.getAllTitulaires();
		assertThat(titulaires)
				.usingRecursiveComparison()
				.isEqualTo(expected);
		
	}
	
	
	
}
