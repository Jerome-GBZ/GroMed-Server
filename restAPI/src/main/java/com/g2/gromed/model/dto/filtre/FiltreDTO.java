package com.g2.gromed.model.dto.filtre;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(name = "Filtre", description = "filtre pour la recherche de présentation")
public class FiltreDTO {

    @Schema(description = "Liste de tous les titulaires de médicaments", example = "[{titulaire: 'Sanofi']")
    List<String> titulaires;

    @Schema(description = "Liste de toutes les dénomations de substances de médicaments", example = "[{composition: 'H1N1']")
    List<String> substancesDenomitations;
}
