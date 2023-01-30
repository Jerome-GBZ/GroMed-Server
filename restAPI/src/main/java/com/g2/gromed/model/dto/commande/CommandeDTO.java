package com.g2.gromed.model.dto.commande;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Schema(description = "DTO de la commande", name = "CommandeModel")
public class CommandeDTO {
    @Schema(description = "Numero de la commande", example = "123456")
    private int numeroCommande;

    @Schema(description = "Date de la commande", example = "12/02/2022")
    private Date dateCommande;

    @Schema(description = "Status de la commande", example = "En cours")
    private String status;

    @Schema(description = "Indique si la livraison est livrée en une fois", example = "true")
    private boolean delivered;

    @Schema(description = "Total de la commande", example = "12.5")
    private double total;

    @Schema(description = "Nombre de presentation dans la commande", example = "12")
    private int nombrePresentation;
}