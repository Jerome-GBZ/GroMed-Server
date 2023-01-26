package com.g2.gromed.service;

import com.g2.gromed.composant.CommandeComposant;
import com.g2.gromed.composant.PresentationComposant;
import com.g2.gromed.composant.UtilisateurComposant;
import com.g2.gromed.entity.*;
import com.g2.gromed.mapper.ICommandeMapper;
import com.g2.gromed.mapper.IConditionDelivranceMapper;
import com.g2.gromed.mapper.IInfoImportanteMapper;
import com.g2.gromed.mapper.IUtilisateurMapper;
import com.g2.gromed.model.dto.commande.ConditionPrescriptionDTO;
import com.g2.gromed.model.dto.commande.PresentationPanierDTO;
import com.g2.gromed.model.dto.presentation.InfoImportanteDTO;
import com.g2.gromed.model.dto.utilisateur.UtilisateurDTO;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Log
@Service
@AllArgsConstructor
public class CommandeService {
	private ICommandeMapper commandeMapper;
	private IInfoImportanteMapper infoImportanteMapper;
	private IUtilisateurMapper utilisateurMapper;
	private IConditionDelivranceMapper conditionDelivranceMapper;
	private CommandeComposant commandeComposant;
	
	private UtilisateurComposant utilisateurComposant;
	
	private PresentationComposant presentationComposant;
	
	public UtilisateurDTO addPresentationToCart(String email, String codeCIP7, int quantite) {
		Utilisateur utilisateur = utilisateurComposant.getUserByEmail(email);
		Presentation presentation = presentationComposant.getPresentationByCodeCIP7(codeCIP7);
		if(presentation == null || utilisateur == null || quantite <= 0){
			return null;
		}
		Commande commande = commandeComposant.getCart(email);
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
		return utilisateurMapper.toUtilisateurDTO(utilisateur,commandeComposant.countCartPresentation(commande.getNumeroCommande()));
	}
	
	public UtilisateurDTO deletePresentationFromCart(String email, String codeCIP7 ) {
		Utilisateur utilisateur = utilisateurComposant.getUserByEmail(email);
		Presentation presentation = presentationComposant.getPresentationByCodeCIP7(codeCIP7);
		Commande commande = commandeComposant.getCart(email);
		if(presentation == null || utilisateur == null || commande == null){
			return null;
		}
		commandeComposant.removeFromCart(commandeComposant.findFirstByNumeroCommandeAndCodeCIP7(commande.getNumeroCommande(), codeCIP7));
		return utilisateurMapper.toUtilisateurDTO(utilisateur,commandeComposant.countCartPresentation(commande.getNumeroCommande()));
	}
	
	public List<PresentationPanierDTO> getCart(String email) {
		Utilisateur utilisateur = utilisateurComposant.getUserByEmail(email);
		Commande commande = commandeComposant.getCart(email);
		if(utilisateur == null || commande == null){
			return Collections.emptyList();
		}
		List<CommandeMedicament> panier = commande.getCommandeMedicaments();
		return panier
				.stream()
				.map(cm -> {
					List<InfoImportanteDTO> infoImportantes = cm.getPresentation().getMedicament().getInfoImportantes().stream().map(infoImportanteMapper::toInfoImportanteDTO).toList();
					List<ConditionPrescriptionDTO> conditionPrescriptions = cm.getPresentation().getMedicament().getConditionDelivrances().stream().map(conditionDelivranceMapper::conditionDelivranceToConditionPrescriptionDTO).toList();
					return commandeMapper.commandeMedicamentToPresentationPanierDTO(cm,infoImportantes,conditionPrescriptions);
				})
				.toList();
	}
}
