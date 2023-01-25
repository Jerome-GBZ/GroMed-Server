package com.g2.gromed.composant;

import com.g2.gromed.entity.Presentation;
import com.g2.gromed.model.dto.presentation.Pagination;
import com.g2.gromed.repository.IPresentationRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PresentationComposant {
	private IPresentationRepository presentationRepository;
	
	public Page<Presentation> getPresentations(Pagination pagination){
		return presentationRepository.findAll(PageRequest.of(pagination.getPage(), pagination.getSize()));
	}
	
	public Presentation getPresentationByCodeCIP7(String codeCIP7){
		return presentationRepository.findFirstByCodeCIP7(codeCIP7);
	}
}
