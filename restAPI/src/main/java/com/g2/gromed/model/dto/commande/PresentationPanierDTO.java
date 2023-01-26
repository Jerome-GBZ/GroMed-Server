package com.g2.gromed.model.dto.commande;

import com.g2.gromed.model.dto.presentation.InfoImportanteDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(name = "PresentationPanierModel", description = "DTO représentant une présentation dans le panier")
public class PresentationPanierDTO {
	@Schema(description = "code CIP7 de la présentation", example = "3400935")
	private String codeCIP7;
	@Schema(description = "nom de la présentation", example = "DOLIPRANE 1000 mg")
	private String denomination;
	
	@Schema(description = "quantité de la présentation", example = "1")
	private int quantite;
	
	@Schema(description = "prix unitaire de la présentation", example = "1.5")
	private Double prixUnitaire;
	
	@Schema(description = "taux de remboursement", example = "0.5")
	private Double tauxRemboursement;
	
	@Schema(description = "stock de la présentation", example = "10")
	private int stock;
	
	@Schema(description = "liste des informations importantes de la présentation")
	private List<InfoImportanteDTO> infoImportantes;
	
	@Schema(description = "liste des conditions de prescription de la présentation")
	private List<ConditionPrescriptionDTO> conditionsPrescription;
	
	
}
