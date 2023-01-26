package com.g2.gromed.service;

import com.g2.gromed.composant.PresentationComposant;
import com.g2.gromed.entity.Presentation;
import com.g2.gromed.mapper.IInfoImportanteMapper;
import com.g2.gromed.mapper.IPresentationMapper;
import com.g2.gromed.model.dto.presentation.InfoImportanteDTO;
import com.g2.gromed.model.dto.presentation.PresentationCardDTO;
import com.g2.gromed.model.dto.presentation.PresentationDetailDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class PresentationService {
	private PresentationComposant presentationComposant;
	private IPresentationMapper presentationMapper;
	private IInfoImportanteMapper infoImportanteMapper;

	public Page<PresentationCardDTO> getAllPresentations(Pageable pagination) {
		Page<Presentation> presentations = presentationComposant.getPresentations(pagination);
		return presentations.map(presentationMapper::toPresentationCardDTO);
	}

	public PresentationDetailDTO getDetailPresentation(String codeCIP7) {
		Presentation presentation = presentationComposant.getPresentationByCodeCIP7(codeCIP7);
		if(null == presentation){
			return null;
		}
		List<InfoImportanteDTO> infoImportanteDTOList = presentation.getMedicament().getInfoImportantes()
				.stream()
				.map(infoImportanteMapper::toInfoImportanteDTO)
				.toList();
		return presentationMapper.toPresentationDetailDTO(presentation, infoImportanteDTOList);
	}
}