package com.g2.gromed.mapper;

import com.g2.gromed.entity.ConditionDelivrance;
import com.g2.gromed.model.dto.commande.ConditionPrescriptionDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-27T11:30:59+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 18.0.2.1 (Oracle Corporation)"
)
@Component
public class IConditionDelivranceMapperImpl implements IConditionDelivranceMapper {

    @Override
    public ConditionPrescriptionDTO conditionDelivranceToConditionPrescriptionDTO(ConditionDelivrance conditionDelivrance) {
        if ( conditionDelivrance == null ) {
            return null;
        }

        ConditionPrescriptionDTO conditionPrescriptionDTO = new ConditionPrescriptionDTO();

        conditionPrescriptionDTO.setCondition( conditionDelivrance.getCondition() );

        return conditionPrescriptionDTO;
    }
}
