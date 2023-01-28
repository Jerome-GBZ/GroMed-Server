package com.g2.gromed.mapper;

import com.g2.gromed.entity.Livraison;
import com.g2.gromed.model.dto.commande.LivraisonDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ILivraisonMapper {

	@Mapping(target = "dateLivraison", source = "livraison.dateLivraison")
	@Mapping(target = "delivered", source = "delivered")
	LivraisonDTO livraisontoLivraisonDTO(Livraison livraison, boolean delivered);
}
