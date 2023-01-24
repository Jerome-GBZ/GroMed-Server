package com.g2.gromed.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(name = "PresentationCardModel", description = "DTO représentant une présentation")
public class PresentationCardDTO {
	@Schema(description = "Code CIS du médicament", example = "1234567")
	private String codeCIS;
	
	@Schema(description = "Code CIP7 de la présentation", example = "1234567")
	private String codeCIP7;
	
	@Schema(description = "Titre de la présent", example = "Titre de la présentation")
	private String titre;
	
	@Schema(description = "Indique si le médicament contient des informations importante", example = "true")
	private Boolean contientInformation;
	
	@Schema(description = "Prix de la présentation", example = "12.5")
	private Double prix;
	
	@Schema(description = "Description de la présentation", example = "Description de la présentation")
	private String description;
	
	@Schema(description = "Indique si le médicament est générique", example = "true")
	private boolean estGenerique;
	
	@Schema(description = "Indique la forme du médicament", example = "Comprimé sécable")
	private String format;
}
