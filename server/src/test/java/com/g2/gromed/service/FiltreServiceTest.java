package com.g2.gromed.service;

import com.g2.gromed.GromedApplication;
import com.g2.gromed.composant.FiltreComposant;
import com.g2.gromed.model.dto.filtre.FiltreDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest(classes = GromedApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class FiltreServiceTest {
	@MockBean
	FiltreComposant filtreComposant;
	
	@Autowired
	FiltreService filtreService;
	
	@Test
	void getAllFilters(){
		when(filtreComposant.getAllTitulaires()).thenReturn(List.of("Titulaire 1", "Titulaire 2"));
		when(filtreComposant.getAllSubstancesDenomination()).thenReturn(List.of("Substance 1", "Substance 2"));
		FiltreDTO expectedFiltreDTO = new FiltreDTO();
		expectedFiltreDTO.setTitulaires(List.of("Titulaire 1", "Titulaire 2"));
		expectedFiltreDTO.setSubstancesDenomitations(List.of("Substance 1", "Substance 2"));
		FiltreDTO filtreDTO = filtreService.getAllFilters();
		assertThat(filtreDTO)
				.usingRecursiveComparison()
				.isEqualTo(expectedFiltreDTO);
	}
}
