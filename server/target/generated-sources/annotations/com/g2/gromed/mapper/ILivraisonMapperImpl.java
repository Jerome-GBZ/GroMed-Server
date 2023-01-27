package com.g2.gromed.mapper;

import com.g2.gromed.entity.Livraison;
import com.g2.gromed.model.dto.commande.LivraisonDTO;
import java.sql.Date;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-27T11:30:59+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 18.0.2.1 (Oracle Corporation)"
)
@Component
public class ILivraisonMapperImpl implements ILivraisonMapper {

    @Override
    public LivraisonDTO livraisontoLivraisonDTO(Livraison livraison, boolean delivered) {
        if ( livraison == null ) {
            return null;
        }

        LivraisonDTO livraisonDTO = new LivraisonDTO();

        if ( livraison != null ) {
            if ( livraison.getDateLivraison() != null ) {
                livraisonDTO.setDateLivraison( new Date( livraison.getDateLivraison().getTime() ) );
            }
        }
        livraisonDTO.setDelivered( delivered );

        return livraisonDTO;
    }
}
