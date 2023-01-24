package com.g2.gromed.model.dto.presentation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Pagination", description = "Pagination pour la recherche de présentation")
public class Pagination {

	@Schema(description = "Numéro de la page", example = "1")
	private int page;

	@Schema(description = "Nombre d'éléments par page", example = "10")
	private int size;

}
