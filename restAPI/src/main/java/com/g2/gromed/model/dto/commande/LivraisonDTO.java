package com.g2.gromed.model.dto.commande;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Schema(description = "DTO de la livraison", name = "LivraisonModel")
public class LivraisonDTO {
	@Schema(description = "Date d'envoie de la livraison", example = "12/02/2022")
	private Instant dateLivraison;
	
	@Schema(description = "Indique si la livraison est livrée en une fois", example = "true")
	private boolean delivered;
}
