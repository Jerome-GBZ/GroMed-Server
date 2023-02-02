package com.g2.gromed.model.dto.presentation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Les compositions d'un médicament",name = "CompositionDTOModel")
public class CompositionDTO {
	
	@Schema(description="Le code de la substance",example ="1450" )
	private String codeSubstance;
	
	@Schema(description="La dénomination de la substance",example ="ABUFENE" )
	private String denominationSubstance;
	
	@Schema(description="La désignation de l'élément pharmaceutique",example ="comprimé" )
	private String designationElementPharmaceutique;
	
	@Schema(description="Le dosage de la substance",example ="0.5g" )
	private String dosage;
	
	@Schema(description="La référence du dosage",example ="1000 ml" )
	private String referenceDosage;
	
	@Schema(description="La nature du composant",example ="substance active(SA)" )
	private String natureComposant;
}
