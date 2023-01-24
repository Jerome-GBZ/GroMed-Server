package com.g2.gromed.controller;

import com.g2.gromed.model.Pagination;
import com.g2.gromed.model.dto.PresentationCardDTO;
import com.g2.gromed.endpoint.IPresentationEndpoint;
import com.g2.gromed.service.PresentationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
public class PresentationController implements IPresentationEndpoint {
	
	private PresentationService presentationService;
	
	@Override
	public ResponseEntity<Page<PresentationCardDTO>> getAllPresentations(@ModelAttribute("pagination") Pagination pagination){
		return ResponseEntity.ok(presentationService.getAllPresentations(pagination));
	}

}
