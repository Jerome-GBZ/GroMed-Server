package com.g2.gromed.mapper;

import com.g2.gromed.entity.CommandeMedicament;
import com.g2.gromed.entity.Medicament;
import com.g2.gromed.entity.Presentation;
import com.g2.gromed.model.dto.commande.ConditionPrescriptionDTO;
import com.g2.gromed.model.dto.commande.PresentationPanierDTO;
import com.g2.gromed.model.dto.presentation.InfoImportanteDTO;
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
public class ICommandeMapperImpl implements ICommandeMapper {

    @Override
    public PresentationPanierDTO commandeMedicamentToPresentationPanierDTO(CommandeMedicament commandeMedicament, List<InfoImportanteDTO> infoImportantes, List<ConditionPrescriptionDTO> conditionDelivrances) {
        if ( commandeMedicament == null && infoImportantes == null && conditionDelivrances == null ) {
            return null;
        }

        PresentationPanierDTO presentationPanierDTO = new PresentationPanierDTO();

        if ( commandeMedicament != null ) {
            presentationPanierDTO.setCodeCIP7( commandeMedicamentPresentationCodeCIP7( commandeMedicament ) );
            presentationPanierDTO.setDenomination( commandeMedicamentPresentationMedicamentDenomination( commandeMedicament ) );
            presentationPanierDTO.setQuantite( commandeMedicament.getQuantite() );
            presentationPanierDTO.setPrixUnitaire( commandeMedicamentPresentationPrix( commandeMedicament ) );
            presentationPanierDTO.setTauxRemboursement( commandeMedicamentPresentationTauxRemboursement( commandeMedicament ) );
            presentationPanierDTO.setStock( commandeMedicamentPresentationStock( commandeMedicament ) );
        }
        List<InfoImportanteDTO> list = infoImportantes;
        if ( list != null ) {
            presentationPanierDTO.setInfoImportantes( new ArrayList<InfoImportanteDTO>( list ) );
        }
        List<ConditionPrescriptionDTO> list1 = conditionDelivrances;
        if ( list1 != null ) {
            presentationPanierDTO.setConditionsPrescription( new ArrayList<ConditionPrescriptionDTO>( list1 ) );
        }

        return presentationPanierDTO;
    }

    private String commandeMedicamentPresentationCodeCIP7(CommandeMedicament commandeMedicament) {
        if ( commandeMedicament == null ) {
            return null;
        }
        Presentation presentation = commandeMedicament.getPresentation();
        if ( presentation == null ) {
            return null;
        }
        String codeCIP7 = presentation.getCodeCIP7();
        if ( codeCIP7 == null ) {
            return null;
        }
        return codeCIP7;
    }

    private String commandeMedicamentPresentationMedicamentDenomination(CommandeMedicament commandeMedicament) {
        if ( commandeMedicament == null ) {
            return null;
        }
        Presentation presentation = commandeMedicament.getPresentation();
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

    private Double commandeMedicamentPresentationPrix(CommandeMedicament commandeMedicament) {
        if ( commandeMedicament == null ) {
            return null;
        }
        Presentation presentation = commandeMedicament.getPresentation();
        if ( presentation == null ) {
            return null;
        }
        Double prix = presentation.getPrix();
        if ( prix == null ) {
            return null;
        }
        return prix;
    }

    private Double commandeMedicamentPresentationTauxRemboursement(CommandeMedicament commandeMedicament) {
        if ( commandeMedicament == null ) {
            return null;
        }
        Presentation presentation = commandeMedicament.getPresentation();
        if ( presentation == null ) {
            return null;
        }
        Double tauxRemboursement = presentation.getTauxRemboursement();
        if ( tauxRemboursement == null ) {
            return null;
        }
        return tauxRemboursement;
    }

    private int commandeMedicamentPresentationStock(CommandeMedicament commandeMedicament) {
        if ( commandeMedicament == null ) {
            return 0;
        }
        Presentation presentation = commandeMedicament.getPresentation();
        if ( presentation == null ) {
            return 0;
        }
        int stock = presentation.getStock();
        return stock;
    }
}
