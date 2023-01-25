package com.g2.gromed.service;

import com.g2.gromed.composant.CommandeComposant;
import com.g2.gromed.composant.PresentationComposant;
import com.g2.gromed.composant.UtilisateurComposant;
import com.g2.gromed.entity.*;
import com.g2.gromed.mapper.ICommandeMapper;
import com.g2.gromed.mapper.IConditionDelivranceMapper;
import com.g2.gromed.mapper.IInfoImportanteMapper;
import com.g2.gromed.model.dto.commande.ConditionPrescriptionDTO;
import com.g2.gromed.model.dto.commande.PresentationPanierDTO;
import com.g2.gromed.model.dto.presentation.InfoImportanteDTO;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Log
@Service
@AllArgsConstructor
public class CommandeService {
	private ICommandeMapper commandeMapper;
	private IInfoImportanteMapper infoImportanteMapper;
	private IConditionDelivranceMapper conditionDelivranceMapper;
	private CommandeComposant commandeComposant;
	
	private UtilisateurComposant utilisateurComposant;
	
	private PresentationComposant presentationComposant;
	
	public Integer addPresentationToCart(Long idUtilisateur, String codeCIP7, int quantite) {
		Utilisateur utilisateur = utilisateurComposant.getUserById(idUtilisateur);
		Presentation presentation = presentationComposant.getPresentationByCodeCIP7(codeCIP7);
		if(presentation == null || utilisateur == null || quantite <= 0){
			return null;
		}
		Commande commande = commandeComposant.getCart(idUtilisateur);
		if(commande == null){
			commande = new Commande();
			commande.setUtilisateur(utilisateur);
			commande.setStatus(StatusCommande.PANIER);
			commande.setDateCommande(new Date());
			commande = commandeComposant.createNewCommande(commande);
			log.info("Commande created: " + commande.getNumeroCommande());
		}else{
			log.info("Commande found: " + commande.getNumeroCommande());
		}
		
		CommandeMedicament commandeMedicament = commandeComposant.findFirstByNumeroCommandeAndCodeCIP7(commande.getNumeroCommande(), codeCIP7);
		log.info("CommandeMedicament found: " + commandeMedicament);
		if(commandeMedicament == null){
			CommandeMedicament newMedicament = new CommandeMedicament();
			newMedicament.setQuantite(quantite);
			newMedicament.setCommande(commande);
			newMedicament.setPresentation(presentation);
			commandeComposant.addToCart(newMedicament);
		}else{
			commandeMedicament.setQuantite(commandeMedicament.getQuantite() + quantite);
			commandeComposant.addToCart(commandeMedicament);
		}
		return commandeComposant.countCartPresentation(commande.getNumeroCommande());
	}
	
	public Integer deletePresentationFromCart(Long idUtilisateur, String codeCIP7 ) {
		Utilisateur utilisateur = utilisateurComposant.getUserById(idUtilisateur);
		Presentation presentation = presentationComposant.getPresentationByCodeCIP7(codeCIP7);
		Commande commande = commandeComposant.getCart(idUtilisateur);
		if(presentation == null || utilisateur == null || commande == null){
			return null;
		}
		commandeComposant.removeFromCart(commandeComposant.findFirstByNumeroCommandeAndCodeCIP7(commande.getNumeroCommande(), codeCIP7));
		return commandeComposant.countCartPresentation(commande.getNumeroCommande());
	}
	
	public List<PresentationPanierDTO> getCart(Long idUtilisateur) {
		Utilisateur utilisateur = utilisateurComposant.getUserById(idUtilisateur);
		Commande commande = commandeComposant.getCart(idUtilisateur);
		if(utilisateur == null || commande == null){
			return null;
		}
		List<CommandeMedicament> panier = commande.getCommandeMedicaments();
		return panier
				.stream()
				.map(cm -> {
					List<InfoImportanteDTO> infoImportantes = cm.getPresentation().getMedicament().getInfoImportantes().stream().map(infoImportanteMapper::toInfoImportanteDTO).collect(Collectors.toList());
					List<ConditionPrescriptionDTO> conditionPrescriptions = cm.getPresentation().getMedicament().getConditionDelivrances().stream().map(conditionDelivranceMapper::conditionDelivranceToConditionPrescriptionDTO).collect(Collectors.toList());
					return commandeMapper.commandeMedicamentToPresentationPanierDTO(cm,infoImportantes,conditionPrescriptions);
				})
				.collect(Collectors.toList());
	}
}
