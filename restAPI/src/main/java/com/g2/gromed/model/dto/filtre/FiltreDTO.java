package com.g2.gromed.model.dto.filtre;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(name = "Filtres", description = "filtres pour la recherche de présentations")
public class FiltreDTO {

    @Schema(description = "Le nom de la présentation du médicament", example = "ABUFENE 400 mg, comprimé")
    String presentationName;

    @Schema(description = "Indique si la présentation du médicament est en stock", example = "false")
    boolean isAvailable;

    @Schema(description = "Indique si la présentation est celle d'une médicament original", example = "true")
    boolean isOriginal;

    @Schema(description = "Indique si la présentation est celle d'une médicament générique", example = "true")
    boolean isGenerique;

    @Schema(description = "Liste de tous les titulaires de médicaments", example = "[\"Boirons\", \"Sanofi\"]")
    List<String> titulaires;

    @Schema(description = "Liste de toutes les dénominations de substances de médicaments", example = "[{composition: 'H1N1']")
    List<String> substancesDenomitations;
}
