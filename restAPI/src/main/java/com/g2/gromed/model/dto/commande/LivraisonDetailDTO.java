package com.g2.gromed.model.dto.commande;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Schema(description = "détail d'une livraison", name = "LivraisonDetailDTO")
public class LivraisonDetailDTO {
	
	@Schema(description = "Date d'envoie de la livraison", example = "12/02/2022")
	private Instant dateLivraison;
	
	@Schema(description = "recap des presentations de la livraison")
	private List<PresentationRecapCommandeDTO> recapLivraison;
}
