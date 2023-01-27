package com.g2.gromed.mapper;

import com.g2.gromed.entity.Medicament;
import com.g2.gromed.entity.Presentation;
import com.g2.gromed.model.dto.presentation.InfoImportanteDTO;
import com.g2.gromed.model.dto.presentation.PresentationCardDTO;
import com.g2.gromed.model.dto.presentation.PresentationDetailDTO;
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
public class IPresentationMapperImpl implements IPresentationMapper {

    @Override
    public PresentationCardDTO toPresentationCardDTO(Presentation presentation) {
        if ( presentation == null ) {
            return null;
        }

        PresentationCardDTO presentationCardDTO = new PresentationCardDTO();

        presentationCardDTO.setCodeCIS( presentationMedicamentCodeCIS( presentation ) );
        presentationCardDTO.setTitre( presentationMedicamentDenomination( presentation ) );
        presentationCardDTO.setDescription( presentation.getDenomination() );
        presentationCardDTO.setFormat( presentationMedicamentFormePharmaceutique( presentation ) );
        presentationCardDTO.setCodeCIP7( presentation.getCodeCIP7() );
        presentationCardDTO.setPrix( presentation.getPrix() );

        presentationCardDTO.setEstGenerique( !presentation.getMedicament().getGroupeGeneriques().isEmpty() );
        presentationCardDTO.setContientInformation( !presentation.getMedicament().getInfoImportantes().isEmpty() );

        return presentationCardDTO;
    }

    @Override
    public PresentationDetailDTO toPresentationDetailDTO(Presentation presentation, List<InfoImportanteDTO> infoImportanteDTOList) {
        if ( presentation == null && infoImportanteDTOList == null ) {
            return null;
        }

        PresentationDetailDTO presentationDetailDTO = new PresentationDetailDTO();

        if ( presentation != null ) {
            presentationDetailDTO.setCodeCIS( presentationMedicamentCodeCIS( presentation ) );
            presentationDetailDTO.setCodeCIP7( presentation.getCodeCIP7() );
            presentationDetailDTO.setTitre( presentationMedicamentDenomination( presentation ) );
            presentationDetailDTO.setDescription( presentation.getDenomination() );
            presentationDetailDTO.setPrix( presentation.getPrix() );
            presentationDetailDTO.setStock( presentation.getStock() );
            presentationDetailDTO.setFormePharmaceutique( presentationMedicamentFormePharmaceutique( presentation ) );
            presentationDetailDTO.setTitulaire( presentationMedicamentTitulaire( presentation ) );
            presentationDetailDTO.setTauxRemboursement( presentation.getTauxRemboursement() );
        }
        List<InfoImportanteDTO> list = infoImportanteDTOList;
        if ( list != null ) {
            presentationDetailDTO.setInformationsImportantes( new ArrayList<InfoImportanteDTO>( list ) );
        }
        presentationDetailDTO.setEstGenerique( !presentation.getMedicament().getGroupeGeneriques().isEmpty() );

        return presentationDetailDTO;
    }

    private String presentationMedicamentCodeCIS(Presentation presentation) {
        if ( presentation == null ) {
            return null;
        }
        Medicament medicament = presentation.getMedicament();
        if ( medicament == null ) {
            return null;
        }
        String codeCIS = medicament.getCodeCIS();
        if ( codeCIS == null ) {
            return null;
        }
        return codeCIS;
    }

    private String presentationMedicamentDenomination(Presentation presentation) {
        if ( presentation == null ) {
            return null;
        }
        Medicament medicament = presentation.getMedicament();
        if ( medicament == null ) {
            return null;
        }
        String denomination = medicament.getDenomination();
        if ( denomination == null ) {
            return null;
        }
        return denomination;
    }

    private String presentationMedicamentFormePharmaceutique(Presentation presentation) {
        if ( presentation == null ) {
            return null;
        }
        Medicament medicament = presentation.getMedicament();
        if ( medicament == null ) {
            return null;
        }
        String formePharmaceutique = medicament.getFormePharmaceutique();
        if ( formePharmaceutique == null ) {
            return null;
        }
        return formePharmaceutique;
    }

    private String presentationMedicamentTitulaire(Presentation presentation) {
        if ( presentation == null ) {
            return null;
        }
        Medicament medicament = presentation.getMedicament();
        if ( medicament == null ) {
            return null;
        }
        String titulaire = medicament.getTitulaire();
        if ( titulaire == null ) {
            return null;
        }
        return titulaire;
    }
}
