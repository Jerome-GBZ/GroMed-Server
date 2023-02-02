package com.g2.gromed.mapper;

import com.g2.gromed.entity.Livraison;
import com.g2.gromed.entity.LivraisonPresentation;
import com.g2.gromed.model.dto.commande.LivraisonDTO;
import com.g2.gromed.model.dto.commande.LivraisonDetailDTO;
import com.g2.gromed.model.dto.commande.PresentationRecapCommandeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface ILivraisonMapper {

	@Mapping(target = "dateLivraison", source = "livraison.dateLivraison")
	@Mapping(target = "delivered", source = "delivered")
	LivraisonDTO livraisontoLivraisonDTO(Livraison livraison,boolean delivered);
	
	@Mapping(source = "livraisonPresentation.presentation.medicament.denomination", target = "nomPresentation")
	@Mapping(source = "livraisonPresentation.quantite", target = "quantite")
	PresentationRecapCommandeDTO livraisonPresentationToPresentationRecapCommandeDTO(LivraisonPresentation livraisonPresentation);
	
	@Mapping(target = "dateLivraison", source = "livraison.dateLivraison")
	@Mapping(target = "recapLivraison", source = "recapLivraisonDTO")
	LivraisonDetailDTO livraisonToLivraisonDetailDTO(Livraison livraison, List<PresentationRecapCommandeDTO> recapLivraisonDTO);
}
