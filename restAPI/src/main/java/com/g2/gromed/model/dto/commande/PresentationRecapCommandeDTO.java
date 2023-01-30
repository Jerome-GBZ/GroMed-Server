package com.g2.gromed.model.dto.commande;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "recap des presentations de la livraison", name = "PresentationRecapCommandeDTO")
public class PresentationRecapCommandeDTO {

	@Schema(description = "nom de la presentation", example = "paracetamol")
	private String nomPresentation;
	@Schema(description = "quantité de la presentation", example = "10")
	private int quantite;
}
