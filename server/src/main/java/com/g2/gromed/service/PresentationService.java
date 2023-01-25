package com.g2.gromed.service;

import com.g2.gromed.composant.PresentationComposant;
import com.g2.gromed.mapper.IInfoImportanteMapper;
import com.g2.gromed.model.dto.presentation.Pagination;
import com.g2.gromed.model.dto.presentation.PresentationCardDTO;
import com.g2.gromed.entity.Presentation;
import com.g2.gromed.mapper.IPresentationMapper;
import com.g2.gromed.model.dto.presentation.PresentationDetailDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class PresentationService {
	private PresentationComposant presentationComposant;
	
	private IPresentationMapper presentationMapper;
	private IInfoImportanteMapper infoImportanteMapper;
	public Page<PresentationCardDTO> getAllPresentations( Pagination pagination) {
		Page<Presentation> presentations = presentationComposant.getAllPresentations( pagination);
		return presentations.map(presentationMapper::presentationToPresentationCardDTO);
	}
	public PresentationDetailDTO getPresentationByCodeCIP7(String codeCIP7) {
		Presentation presentation = presentationComposant.getPresentationByCodeCIP7(codeCIP7);
		PresentationDetailDTO detail = presentationMapper.presentationToPresentationDetailDTO(presentation);
		detail.setInformationsImportantes(presentation
				.getMedicament()
				.getInfoImportantes()
				.stream()
				.map(infoImportanteMapper::infoImportanteToInfoImportanteDTO)
				.collect(Collectors.toList()));
		return detail;
	}
}
