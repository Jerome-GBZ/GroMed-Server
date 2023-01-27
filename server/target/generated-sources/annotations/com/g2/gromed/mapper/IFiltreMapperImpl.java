package com.g2.gromed.mapper;

import com.g2.gromed.model.dto.filtre.FiltreDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-27T11:30:59+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 18.0.2.1 (Oracle Corporation)"
)
@Component
public class IFiltreMapperImpl implements IFiltreMapper {

    @Override
    public FiltreDTO toFiltreDTO(List<String> fTitulaires, List<String> fCompositions) {
        if ( fTitulaires == null && fCompositions == null ) {
            return null;
        }

        FiltreDTO filtreDTO = new FiltreDTO();

        List<String> list = fTitulaires;
        if ( list != null ) {
            filtreDTO.setTitulaires( new ArrayList<String>( list ) );
        }
        List<String> list1 = fCompositions;
        if ( list1 != null ) {
            filtreDTO.setSubstancesDenomitations( new ArrayList<String>( list1 ) );
        }

        return filtreDTO;
    }
}
