package com.g2.gromed.mapper;

import com.g2.gromed.dto.PresentationCardDTO;
import com.g2.gromed.entity.Presentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper()
public interface IPresentationMapper {

	@Mapping(target="estGenerique",expression = "java(presentation.getMedicament().getGroupeGeneriques().isEmpty())")
	@Mapping(target="contientInformation",expression = "java(presentation.getMedicament().getInfoImportantes().isEmpty())")
	@Mapping(target="codeCIS",source="presentation.medicament.codeCIS")
	@Mapping(target="titre",source="presentation.medicament.denomination")
	@Mapping(target="description",source="presentation.denomination")
	@Mapping(target="format",source="presentation.medicament.formePharmaceutique")
	PresentationCardDTO presentationToPresentationCardDTO(Presentation presentation);
	
	
}
