package com.g2.gromed.composant;

import com.g2.gromed.entity.Presentation;
import com.g2.gromed.model.Filtre;
import com.g2.gromed.model.Pagination;
import com.g2.gromed.repository.PresentationRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PresentationComposant {
	private PresentationRepository presentationRepository;
	
	public Page<Presentation> getAllPresentations( Pagination pagination){
		return presentationRepository.findAll(PageRequest.of(pagination.getPage(), pagination.getSize()));
	}
}
