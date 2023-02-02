package com.g2.gromed.mapper;

import com.g2.gromed.entity.CommandeType;
import com.g2.gromed.model.dto.commandetype.CommandeTypeInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper
public interface ICommandeTypeMapper {
	@Mapping(target = "nbProduit", expression = "java(commandeType.getCommande().getCommandeMedicaments().size())")
	@Mapping(target = "prixTotal", source = "commandeType.commande.total")
	@Mapping(target = "nom", source = "commandeType.name")
	CommandeTypeInfo toCommandeTypeInfo(CommandeType commandeType);
	
}
