package com.g2.gromed.mapper;

import com.g2.gromed.entity.Etablissement;
import com.g2.gromed.entity.Utilisateur;
import com.g2.gromed.model.dto.utilisateur.UtilisateurDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-27T11:30:59+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 18.0.2.1 (Oracle Corporation)"
)
@Component
public class IUtilisateurMapperImpl implements IUtilisateurMapper {

    @Override
    public UtilisateurDTO toUtilisateurDTO(Utilisateur utilisateur, int nbMedicamentsPanier) {
        if ( utilisateur == null ) {
            return null;
        }

        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();

        if ( utilisateur != null ) {
            utilisateurDTO.setEmail( utilisateur.getEmail() );
            utilisateurDTO.setNom( utilisateur.getNom() );
            utilisateurDTO.setPrenom( utilisateur.getPrenom() );
            utilisateurDTO.setNomEtablissement( utilisateurEtablissementNom( utilisateur ) );
        }
        utilisateurDTO.setNbMedicamentsPanier( nbMedicamentsPanier );

        return utilisateurDTO;
    }

    private String utilisateurEtablissementNom(Utilisateur utilisateur) {
        if ( utilisateur == null ) {
            return null;
        }
        Etablissement etablissement = utilisateur.getEtablissement();
        if ( etablissement == null ) {
            return null;
        }
        String nom = etablissement.getNom();
        if ( nom == null ) {
            return null;
        }
        return nom;
    }
}
