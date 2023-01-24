package com.g2.gromed.composant;

import com.g2.gromed.entity.Presentation;
import com.g2.gromed.repository.PresentationRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PresentationComposant {
	private PresentationRepository presentationRepository;
	
	public Page<Presentation> getAllPresentations() {
		return presentationRepository.findAll(PageRequest.of(0, 10));
	}
}
