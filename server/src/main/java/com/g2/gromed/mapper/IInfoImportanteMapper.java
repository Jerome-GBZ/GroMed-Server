package com.g2.gromed.mapper;

import com.g2.gromed.entity.InfoImportante;
import com.g2.gromed.model.dto.presentation.InfoImportanteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper()
public interface IInfoImportanteMapper {
	
	@Mapping(target="message",source="infoImportante.message")
	InfoImportanteDTO toInfoImportanteDTO(InfoImportante infoImportante);
}
