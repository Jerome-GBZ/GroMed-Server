package com.g2.gromed.controller;

import com.g2.gromed.endpoint.IPresentationEndpoint;
import com.g2.gromed.model.dto.filtre.FiltreDTO;
import com.g2.gromed.model.dto.presentation.PresentationCardDTO;
import com.g2.gromed.model.dto.presentation.PresentationDetailDTO;
import com.g2.gromed.service.PresentationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@AllArgsConstructor
public class PresentationController implements IPresentationEndpoint {
	
	private PresentationService presentationService;
	
	@Override
	public ResponseEntity<Page<PresentationCardDTO>> getPresentations(Pageable pagination, FiltreDTO filtreDTO){
		Page<PresentationCardDTO> page = presentationService.getAllPresentations(pagination, filtreDTO);
		return !page.getContent().isEmpty() ? ResponseEntity.ok(page) : ResponseEntity.notFound().build();
	}
	
	@Override
	public ResponseEntity<PresentationDetailDTO> getDetailPresentation(String codeCIP7) {
		PresentationDetailDTO presentation = presentationService.getDetailPresentation(codeCIP7);
		return presentation != null ? ResponseEntity.ok(presentation) : ResponseEntity.notFound().build();
	}
	
	
}
