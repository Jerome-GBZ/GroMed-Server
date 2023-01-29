package com.g2.gromed.mapper;

import com.g2.gromed.entity.Commande;
import com.g2.gromed.entity.CommandeMedicament;
import com.g2.gromed.model.dto.commande.*;
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
	
	@Mapping(source = "commande.dateCommande", target = "dateCommande")
	@Mapping(source = "delivered", target = "delivered")
	@Mapping(source = "livraisonsDTO", target = "livraisons")
	@Mapping(source = "recapCommandeDTO", target = "recapCommande")
	CommandeDetailDTO commandeToCommandeDetailDTO(Commande commande,boolean delivered ,List<LivraisonDetailDTO> livraisonsDTO, List<PresentationRecapCommandeDTO> recapCommandeDTO);
	
	@Mapping(source = "commandeMedicament.presentation.medicament.denomination", target = "nomPresentation")
	@Mapping(source = "commandeMedicament.quantite", target = "quantite")
	
	PresentationRecapCommandeDTO commandeMedicamentToPresentationRecapCommandeDTO(CommandeMedicament commandeMedicament);
}
