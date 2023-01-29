package com.g2.gromed.model.dto.commande;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Schema(description = "détail d'une commande", name = "CommandeDetailDTO")
public class CommandeDetailDTO {
	@Schema(description = "Date de la commande", example = "12/02/2022")
	private Instant dateCommande;
	
	@Schema(description = "Si la livraison est entièrement livrée ou non", example = "true")
	private boolean delivered;
	
	@Schema(description = "Liste des livraisons de la commande")
	List<LivraisonDetailDTO> livraisons;
	
	@Schema(description = "recap des presentations de la commande")
	List<PresentationRecapCommandeDTO> recapCommande;
	
}
