package com.g2.gromed.controller;

import com.g2.gromed.model.dto.presentation.Pagination;
import com.g2.gromed.model.dto.presentation.PresentationCardDTO;
import com.g2.gromed.endpoint.IPresentationEndpoint;
import com.g2.gromed.model.dto.presentation.PresentationDetailDTO;
import com.g2.gromed.service.PresentationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PresentationController implements IPresentationEndpoint {
	
	private PresentationService presentationService;
	
	@Override
	public ResponseEntity<Page<PresentationCardDTO>> getLesPresentations(@ModelAttribute("pagination") Pagination pagination){
		Page<PresentationCardDTO> page = presentationService.getAllPresentations(pagination);
		return page != null ? ResponseEntity.ok(page) : ResponseEntity.notFound().build();
	}
	
	@Override
	public ResponseEntity<PresentationDetailDTO> getDetailPresentation(String codeCIP7) {
		PresentationDetailDTO presentation = presentationService.getPresentationByCodeCIP7(codeCIP7);
		return presentation != null ? ResponseEntity.ok(presentation) : ResponseEntity.notFound().build();
	}
	
}
