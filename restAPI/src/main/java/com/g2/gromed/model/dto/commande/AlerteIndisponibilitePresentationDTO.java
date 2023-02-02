package com.g2.gromed.model.dto.commande;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Alerte Indisponibilite Presentation DTO", name = "AlerteIndisponibilitePresentationDTO")
public class AlerteIndisponibilitePresentationDTO {
    @Schema(description = "Map des codeCIS avec le stock restant", name = "alertesIndisponibilites", example = "{\"1234567\": 50, \"7654321\": 1}")
    private Map<String, Integer> alertesIndisponibilites;
}
