package com.g2.gromed.service;

import com.g2.gromed.composant.PresentationComposant;
import com.g2.gromed.mapper.IInfoImportanteMapper;
import com.g2.gromed.model.dto.presentation.InfoImportanteDTO;
import com.g2.gromed.model.dto.presentation.Pagination;
import com.g2.gromed.model.dto.presentation.PresentationCardDTO;
import com.g2.gromed.entity.Presentation;
import com.g2.gromed.mapper.IPresentationMapper;
import com.g2.gromed.model.dto.presentation.PresentationDetailDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class PresentationService {
	private PresentationComposant presentationComposant;
	private IPresentationMapper presentationMapper;
	private IInfoImportanteMapper infoImportanteMapper;

	public Page<PresentationCardDTO> getAllPresentations(Pagination pagination) {
		Page<Presentation> presentations = presentationComposant.getPresentations(pagination);
		return presentations.map(presentationMapper::toPresentationCardDTO);
	}

	public PresentationDetailDTO getDetailPresentation(String codeCIP7) {
		Presentation presentation = presentationComposant.getPresentationByCodeCIP7(codeCIP7);
		List<InfoImportanteDTO> infoImportanteDTOList = presentation.getMedicament().getInfoImportantes()
				.stream()
				.map(infoImportanteMapper::toInfoImportanteDTO)
				.collect(Collectors.toList());

		return presentationMapper.toPresentationDetailDTO(presentation, infoImportanteDTOList);
	}
}
