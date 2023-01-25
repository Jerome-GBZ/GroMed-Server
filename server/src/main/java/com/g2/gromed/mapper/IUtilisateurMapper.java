package com.g2.gromed.mapper;

import com.g2.gromed.entity.Utilisateur;
import com.g2.gromed.model.dto.utilisateur.UtilisateurDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper()
public interface IUtilisateurMapper {
	
	@Mapping(target="nom",source="utilisateur.nom")
	@Mapping(target="prenom",source="utilisateur.prenom")
	@Mapping(target="nomEtablissement",source="utilisateur.etablissement.nom")
	UtilisateurDTO utilisateurToUtilisateurDTO(Utilisateur utilisateur);
}
