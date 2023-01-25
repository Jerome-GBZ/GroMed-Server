package com.g2.gromed.mapper;

import com.g2.gromed.entity.Utilisateur;
import com.g2.gromed.model.dto.filtre.FiltreDTO;
import com.g2.gromed.model.dto.utilisateur.UtilisateurDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper()
public interface IFiltreMapper {

    @Mapping(target="titulaires",source="fTitulaires")
    @Mapping(target="substancesDenomitations",source="fCompositions")
    FiltreDTO toFiltreDTO(List<String> fTitulaires, List<String> fCompositions);
}
