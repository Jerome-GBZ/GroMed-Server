package com.g2.gromed.model.dto.commande;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Condition Prescription DTO", name = "ConditionPrescriptionDTO")
public class ConditionPrescriptionDTO {
	@Schema(description = "Condition de prescription", name = "condition", example = "Prescription médicale obligatoire")
	private String condition;
}
