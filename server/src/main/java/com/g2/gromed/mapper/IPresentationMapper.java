package com.g2.gromed.mapper;

import com.g2.gromed.model.dto.presentation.InfoImportanteDTO;
import com.g2.gromed.model.dto.presentation.PresentationCardDTO;
import com.g2.gromed.entity.Presentation;
import com.g2.gromed.model.dto.presentation.PresentationDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper()
public interface IPresentationMapper {

	@Mapping(target="estGenerique",expression = "java(!presentation.getMedicament().getGroupeGeneriques().isEmpty())")
	@Mapping(target="contientInformation",expression = "java(!presentation.getMedicament().getInfoImportantes().isEmpty())")
	@Mapping(target="codeCIS",source="presentation.medicament.codeCIS")
	@Mapping(target="titre",source="presentation.medicament.denomination")
	@Mapping(target="description",source="presentation.denomination")
	@Mapping(target="format",source="presentation.medicament.formePharmaceutique")
	PresentationCardDTO toPresentationCardDTO(Presentation presentation);
	
	@Mapping(target="codeCIS",source="presentation.medicament.codeCIS")
	@Mapping(target="codeCIP7",source="presentation.codeCIP7")
	@Mapping(target="titre",source="presentation.medicament.denomination")
	@Mapping(target="description",source="presentation.denomination")
	@Mapping(target="prix",source="presentation.prix")
	@Mapping(target="stock",source="presentation.stock")
	@Mapping(target="estGenerique",expression = "java(!presentation.getMedicament().getGroupeGeneriques().isEmpty())")
	@Mapping(target="formePharmaceutique",source="presentation.medicament.formePharmaceutique")
	@Mapping(target="titulaire",source="presentation.medicament.titulaire")
	@Mapping(target="tauxRemboursement",source="presentation.tauxRemboursement")
	@Mapping(target="informationsImportantes", source = "infoImportanteDTOList")
	PresentationDetailDTO toPresentationDetailDTO(Presentation presentation, List<InfoImportanteDTO> infoImportanteDTOList);
}
