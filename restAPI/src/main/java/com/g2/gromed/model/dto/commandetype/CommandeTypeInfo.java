package com.g2.gromed.model.dto.commandetype;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Les informations générale d'une commande type",name = "CommandeTypeInfoModel")
public class CommandeTypeInfo {
	
	@Schema(description="Le nom de la commande type",example ="commande generale" )
	private String name;
	@Schema(description="le nombre de presentation dans la commande type",example ="67" )
	private int nbProduit;
	@Schema(description="le montant total de la commande type",example = "5642.25")
	private double prixTotal;
}
