package com.g2.gromed.service;

import com.g2.gromed.composant.PresentationComposant;
import com.g2.gromed.dto.PresentationCardDTO;
import com.g2.gromed.entity.Presentation;
import com.g2.gromed.mapper.IPresentationMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PresentationService {
	private PresentationComposant presentationComposant;
	
	private IPresentationMapper presentationMapper;
	public Page<PresentationCardDTO> getAllPresentations() {
		Page<Presentation> presentations = presentationComposant.getAllPresentations();
		return new PageImpl<>(presentations.getContent()
				.stream()
				.map(presentationMapper::presentationToPresentationCardDTO)
				.collect(Collectors.toList()),
				PageRequest.of(0,10),
				presentations.getTotalElements());
	}
}
