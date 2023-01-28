package com.g2.gromed.mapper;

import com.g2.gromed.entity.Commande;
import com.g2.gromed.entity.CommandeMedicament;
import com.g2.gromed.model.dto.commande.CommandeDTO;
import com.g2.gromed.model.dto.commande.ConditionPrescriptionDTO;
import com.g2.gromed.model.dto.commande.PresentationPanierDTO;
import com.g2.gromed.model.dto.presentation.InfoImportanteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface ICommandeMapper {
	@Mapping(source = "commandeMedicament.presentation.codeCIP7", target = "codeCIP7")
	@Mapping(source = "commandeMedicament.presentation.medicament.denomination", target = "denomination")
	@Mapping(source = "commandeMedicament.quantite", target = "quantite")
	@Mapping(source = "commandeMedicament.presentation.prix", target = "prixUnitaire")
	@Mapping(source = "commandeMedicament.presentation.tauxRemboursement", target = "tauxRemboursement")
	@Mapping(source = "commandeMedicament.presentation.stock", target = "stock")
	@Mapping(source = "infoImportantes", target = "infoImportantes")
	@Mapping(source = "conditionDelivrances", target = "conditionsPrescription")
	PresentationPanierDTO commandeMedicamentToPresentationPanierDTO(CommandeMedicament commandeMedicament, List<InfoImportanteDTO> infoImportantes, List<ConditionPrescriptionDTO> conditionDelivrances);

	@Mapping(source = "commande.numeroCommande", target = "numeroCommande")
	@Mapping(source = "commande.dateCommande", target = "dateCommande")
	@Mapping(source = "commande.status", target = "status")
	@Mapping(target = "delivered", source = "delivered")
	@Mapping(source = "commande.total", target = "total")
	@Mapping(target = "nombrePresentation", expression = "java(commande.getCommandeMedicaments().size())")
	CommandeDTO commandeToCommandeDTO(Commande commande, boolean delivered);
}
