package com.g2.gromed.mapper;

import com.g2.gromed.entity.InfoImportante;
import com.g2.gromed.model.dto.presentation.InfoImportanteDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-27T11:30:59+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 18.0.2.1 (Oracle Corporation)"
)
@Component
public class IInfoImportanteMapperImpl implements IInfoImportanteMapper {

    @Override
    public InfoImportanteDTO toInfoImportanteDTO(InfoImportante infoImportante) {
        if ( infoImportante == null ) {
            return null;
        }

        InfoImportanteDTO infoImportanteDTO = new InfoImportanteDTO();

        infoImportanteDTO.setMessage( infoImportante.getMessage() );
        infoImportanteDTO.setLien( infoImportante.getLien() );

        return infoImportanteDTO;
    }
}
