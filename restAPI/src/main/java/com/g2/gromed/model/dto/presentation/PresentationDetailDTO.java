package com.g2.gromed.model.dto.presentation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(name = "PresentationDetailModel", description = "DTO représentant une présentation détaillée")
public class PresentationDetailDTO {
	@Schema(description = "Code CIS de la présentation", example = "123456")
	private String codeCIS;
	
	@Schema(description = "Code CIP7 de la présentation", example = "1234567")
	private String codeCIP7;
	
	@Schema(description = "Titre de la présentation", example = "Titre de la présentation")
	private String titre;
	
	@Schema(description = "Description de la présentation", example = "Description de la présentation")
	private String description;
	
	@Schema(description = "Prix de la présentation", example = "12.5")
	private Double prix;
	
	@Schema(description = "Stock de la présentation", example = "12")
	private int stock;
	
	@Schema(description = "Indique si le médicament est générique", example = "true")
	private Boolean estGenerique;
	
	@Schema(description = "Indique la forme du médicament", example = "crème")
	private String formePharmaceutique;
	
	@Schema(description = "Indique le titulaire du médicament", example = "Lab 1")
	private String titulaire;
	
	@Schema(description = "Indique le taux de remboursement", example = "0.5")
	private Double tauxRemboursement;
	
	@Schema(description = "Indique les vois d'administration du médicament", example = "voie 1;voie 2")
	private String voiesAdministration;
	
	@Schema(description = "Indique la liste des informations importantes du médicament")
	private List<InfoImportanteDTO> informationsImportantes;
	
	@Schema(description = "Indique la liste des compositions du médicament")
	private List<CompositionDTO> compositions;
}
