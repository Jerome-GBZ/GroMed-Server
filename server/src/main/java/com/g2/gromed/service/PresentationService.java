package com.g2.gromed.service;

import com.g2.gromed.composant.PresentationComposant;
import com.g2.gromed.model.Filtre;
import com.g2.gromed.model.Pagination;
import com.g2.gromed.model.dto.PresentationCardDTO;
import com.g2.gromed.entity.Presentation;
import com.g2.gromed.mapper.IPresentationMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class PresentationService {
	private PresentationComposant presentationComposant;
	
	private IPresentationMapper presentationMapper;
	public Page<PresentationCardDTO> getAllPresentations( Pagination pagination) {
		Page<Presentation> presentations = presentationComposant.getAllPresentations( pagination);
		return presentations.map(presentationMapper::presentationToPresentationCardDTO);
	}
}
