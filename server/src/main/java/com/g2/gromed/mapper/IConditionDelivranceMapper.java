package com.g2.gromed.mapper;

import com.g2.gromed.entity.ConditionDelivrance;
import com.g2.gromed.model.dto.commande.ConditionPrescriptionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface IConditionDelivranceMapper {
	
	@Mapping(source = "conditionDelivrance.condition", target = "condition")
	ConditionPrescriptionDTO conditionDelivranceToConditionPrescriptionDTO(ConditionDelivrance conditionDelivrance);
}
