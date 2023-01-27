package com.g2.gromed.model.dto.commande;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlerteIndisponibilitePresentationDTO {
    private Map<String, Integer> alertesIndisponibilites;
}
