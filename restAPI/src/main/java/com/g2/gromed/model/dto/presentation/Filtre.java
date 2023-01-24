package com.g2.gromed.model.dto.presentation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "Filtre", description = "filtre pour la recherche de présentation")
public class Filtre {
	@Schema(description = "recherche par le Code CIP7 de la présentation", example = "1234567")
	private String codeCIP7;
	
	@Schema(description = "recherche par la denomination du medicament", example = "denomination du medicament")
	private String denominationMedicament;

	@Schema(description = "recherche par la denomination de la substance active", example = "Magnésium")
	private String denominationSubstance;
	
	@Schema(description = "recherche en fonction du status, si le médicament est générique", example = "true")
	private Boolean estGenerique;
	@Schema(description = "Recherche en fonction du stock", example = "true")
	private Boolean estDisponible;
	
	@Schema(description = "Recherche par titulaire de médicament", example = "Lab 1")
	private String titulaire;
	@Schema(description = "Numéro de la page", example = "1")
	private int page;
	@Schema(description = "Nombre d'éléments par page", example = "10")
	private int size;
}
