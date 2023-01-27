package com.g2.gromed.service;

import com.g2.gromed.composant.CommandeComposant;
import com.g2.gromed.composant.LivraisonComposant;
import com.g2.gromed.composant.PresentationComposant;
import com.g2.gromed.composant.UtilisateurComposant;
import com.g2.gromed.entity.*;
import com.g2.gromed.mapper.*;
import com.g2.gromed.mapper.*;
import com.g2.gromed.model.dto.commande.AlerteIndisponibilitePresentationDTO;
import com.g2.gromed.model.dto.commande.ConditionPrescriptionDTO;
import com.g2.gromed.model.dto.commande.LivraisonDTO;
import com.g2.gromed.model.dto.commande.PresentationPanierDTO;
import com.g2.gromed.model.dto.presentation.InfoImportanteDTO;
import com.g2.gromed.model.dto.utilisateur.UtilisateurDTO;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.*;

@Log
@Service
@AllArgsConstructor
public class CommandeService {
	private ICommandeMapper commandeMapper;
	private IInfoImportanteMapper infoImportanteMapper;
	private IUtilisateurMapper utilisateurMapper;
	private IConditionDelivranceMapper conditionDelivranceMapper;
	
	private ILivraisonMapper livraisonMapper;
	
	private CommandeComposant commandeComposant;

	private UtilisateurComposant utilisateurComposant;

	private PresentationComposant presentationComposant;
	
	private LivraisonComposant livraisonComposant;
	
	public UtilisateurDTO addPresentationToCart(String email, String codeCIP7, int quantite) {
		Utilisateur utilisateur = utilisateurComposant.getUserByEmail(email);
		Presentation presentation = presentationComposant.getPresentationByCodeCIP7(codeCIP7);
		if(presentation == null || utilisateur == null || quantite <= 0){
			return null;
		}
		Commande commande = commandeComposant.getCart(email);
		if(commande == null){
			commande = createNewCartForUser(utilisateur);
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

    public AlerteIndisponibilitePresentationDTO getUnavailablePresentations(String email) {
		Commande commande = commandeComposant.getCart(email);
		if(null == commande || commande.getCommandeMedicaments().isEmpty()){
			return null;
		}

		List<CommandeMedicament> commandeMedicaments = commande.getCommandeMedicaments();
		Map<String, Integer> alertesIndisponibilites = new HashMap<>();
		commandeMedicaments.forEach(commandeMedicament -> {
			String codeCIP7 = commandeMedicament.getPresentation().getCodeCIP7();
			Presentation presentation = presentationComposant.getPresentationByCodeCIP7(codeCIP7);
			int stock = presentation.getStock();
			int quantite = commandeMedicament.getQuantite();
			if(quantite > stock){
				alertesIndisponibilites.put(codeCIP7, stock);
			}
		});

		return new AlerteIndisponibilitePresentationDTO(alertesIndisponibilites);
    }
	
	public LivraisonDTO validateCart(String email) {
		Utilisateur utilisateur = utilisateurComposant.getUserByEmail(email);
		AtomicBoolean inOneTime = new AtomicBoolean(true);
		Commande commande = commandeComposant.getCart(email);
		if(commande == null){
			return null;
		}
		
		commande.setStatus(StatusCommande.EN_COURS);
		List<CommandeMedicament> listCommandeMedicament = commande.getCommandeMedicaments();
		if(listCommandeMedicament.isEmpty()){
			return null;
		}
		
		Livraison livraison = new Livraison();
		livraison.setCommande(commande);
		livraison.setDateLivraison(new Date());
		
		List<LivraisonPresentation> livraisonPresentations =createLivraisonMedicamentsEntity(inOneTime, listCommandeMedicament);
		
		livraison.setLivraisonPresentations(livraisonPresentations);
		livraison = livraisonComposant.saveLivraison(livraison);
		commande.getLivraisons().add(livraison);
		commandeComposant.validateCart(commande);
		createNewCartForUser(utilisateur);
		
		return livraisonMapper.livraisontoLivraisonDTO(livraison, inOneTime.get());
	}
	
	private Commande createNewCartForUser(Utilisateur utilisateur) {
		Commande newCommande = new Commande();
		newCommande.setUtilisateur(utilisateur);
		newCommande.setStatus(StatusCommande.PANIER);
		newCommande.setDateCommande(new Date());
		return commandeComposant.createNewCommande(newCommande);
	}
	
	private List<LivraisonPresentation> createLivraisonMedicamentsEntity(AtomicBoolean inOneTime, List<CommandeMedicament> listCommandeMedicament) {
		List<LivraisonPresentation> livraisonPresentations = new ArrayList<>();
		listCommandeMedicament.forEach(cm -> {
			Presentation presentation = cm.getPresentation();
			LivraisonPresentation livraisonPresentation = new LivraisonPresentation();
			livraisonPresentation.setPresentation(presentation);
			int stock = presentation.getStock();
			presentation.setStock(stock - cm.getQuantite());
			if(presentation.getStock()<0){
				inOneTime.set(false);
				livraisonPresentation.setQuantite(stock);
			}else{
				livraisonPresentation.setQuantite(cm.getQuantite());
			}
			livraisonPresentations.add(livraisonPresentation);
			presentationComposant.savePresentation(presentation);
		});
		return livraisonPresentations;
	}
}
