package com.g2.gromed.composant;

import com.g2.gromed.entity.Presentation;
import com.g2.gromed.model.dto.filtre.FiltreDTO;
import com.g2.gromed.repository.IPresentationRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
@Log
public class PresentationComposant {

	@PersistenceContext
	private EntityManager entityManager;

	private IPresentationRepository presentationRepository;
	
	public Page<Presentation> getPresentations(Pageable pagination, FiltreDTO filtreDTO){
		return presentationRepository.getAllFromCriterias(entityManager, filtreDTO, pagination);
	}
	
	public Presentation getPresentationByCodeCIP7(String codeCIP7){
		return presentationRepository.findFirstByCodeCIP7(codeCIP7);
	}
	
	public Presentation updatePresentation(Presentation presentation){
		return presentationRepository.save(presentation);
	}
	
	public List<Presentation> findByCodeCIP7In(List<String> codeCIP7) {
		return presentationRepository.findByCodeCIP7In(codeCIP7);
	}
	
	public void upadteAll(List<Presentation> presentations) {
		presentationRepository.saveAll(presentations);
	}
	
	public List<Presentation> getPresentationWithoutStock() {
		return presentationRepository.findByStockLessThanEqual(0);
	}
}
