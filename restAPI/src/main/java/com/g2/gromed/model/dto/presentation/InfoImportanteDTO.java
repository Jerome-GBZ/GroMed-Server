package com.g2.gromed.model.dto.presentation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "InfoImportanteModel", description = "DTO représentant une information importante")
public class InfoImportanteDTO {
	@Schema(description = "message de l'information importante", example = "Antipsychotiques : rappel des mesures de suivi cardio-métabolique - Point d'Information")
	private String message;
	
	@Schema(description = "lien de l'information importante", example = "https://www.ansm.sante.fr/S-informer/Points-d-information/Points-d-information-2019/PI-2019-01-antipsychotiques-rappel-des-mesures-de-suivi-cardio-metabolique")
	private String lien;
}
