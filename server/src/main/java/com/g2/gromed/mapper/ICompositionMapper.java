package com.g2.gromed.mapper;

import com.g2.gromed.entity.Composition;
import com.g2.gromed.model.dto.presentation.CompositionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ICompositionMapper {
	
	@Mapping(target="codeSubstance",source="composition.codeSubstance")
	@Mapping(target="denominationSubstance",source="composition.denominationSubstance")
	@Mapping(target="designationElementPharmaceutique",source="composition.designationElementPharmaceutique")
	@Mapping(target="dosage",source="composition.dosage")
	@Mapping(target="referenceDosage",source="composition.referenceDosage")
	@Mapping(target="natureComposant",source="composition.natureComposant")
	CompositionDTO toCompositionDTO(Composition composition);
}
