package com.g2.gromed.controller;

import com.g2.gromed.dto.PresentationCardDTO;
import com.g2.gromed.endpoint.IPresentationEndpoint;
import com.g2.gromed.service.PresentationService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PresentationController implements IPresentationEndpoint {
	
	private PresentationService presentationService;
	
	@Override
	public ResponseEntity<Page<PresentationCardDTO>> getAllPresentations() {
		return ResponseEntity.ok(presentationService.getAllPresentations());
	}
}
