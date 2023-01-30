package com.g2.gromed.service;

import com.g2.gromed.composant.CommandeComposant;
import com.g2.gromed.composant.LivraisonComposant;
import com.g2.gromed.composant.PresentationComposant;
import com.g2.gromed.composant.UtilisateurComposant;
import com.g2.gromed.entity.*;
import com.g2.gromed.mapper.*;
import com.g2.gromed.model.dto.commande.*;
import com.g2.gromed.model.dto.presentation.InfoImportanteDTO;
import com.g2.gromed.model.dto.utilisateur.UtilisateurDTO;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Log
@Service
@AllArgsConstructor
public class CommandeService {
	private ICommandeMapper commandeMapper;
	private CommandeComposant commandeComposant;
	private ILivraisonMapper livraisonMapper;
	private LivraisonComposant livraisonComposant;
	private UtilisateurComposant utilisateurComposant;
	private IUtilisateurMapper utilisateurMapper;
	private PresentationComposant presentationComposant;
	private IInfoImportanteMapper infoImportanteMapper;
	private IConditionDelivranceMapper conditionDelivranceMapper;
	
	public UtilisateurDTO addPresentationToUserCart(String email, String codeCIP7, int quantite) {
		Utilisateur utilisateur = utilisateurComposant.getUserByEmail(email);
		Presentation presentation = presentationComposant.getPresentationByCodeCIP7(codeCIP7);
		
		if(presentation == null || utilisateur == null || quantite <= 0) {
			return null;
		}
		
		Commande commande = createCartIfDontExist(utilisateur);
		addPresentationToCart(quantite, presentation, commande);
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
		if(utilisateur == null) {
			return Collections.emptyList();
		}
		Commande commande = createCartIfDontExist(utilisateur);
		List<CommandeMedicament> panier = commande.getCommandeMedicaments();
		return panier.stream()
				.map(cm -> {
					List<InfoImportanteDTO> infoImportantes = cm.getPresentation().getMedicament().getInfoImportantes().stream().map(infoImportanteMapper::toInfoImportanteDTO).toList();
					List<ConditionPrescriptionDTO> conditionPrescriptions = cm.getPresentation().getMedicament().getConditionDelivrances().stream().map(conditionDelivranceMapper::conditionDelivranceToConditionPrescriptionDTO).toList();
					return commandeMapper.commandeMedicamentToPresentationPanierDTO(cm,infoImportantes,conditionPrescriptions);
				}).toList();
	}

    public AlerteIndisponibilitePresentationDTO getUnavailablePresentations(String email) {
	    Utilisateur utilisateur = utilisateurComposant.getUserByEmail(email);
		if(utilisateur == null) {
		    return null;
	    }
		Commande commande = createCartIfDontExist(utilisateur);
		if(null == commande || (commande.getCommandeMedicaments() != null && commande.getCommandeMedicaments().isEmpty())){
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
	
	public LivraisonDTO validateCart(String email, String saveName) {
		Utilisateur utilisateur = utilisateurComposant.getUserByEmail(email);
		AtomicBoolean inOneTime = new AtomicBoolean(true);
		if(utilisateur == null) {
			return null;
		}
		
		Commande commande = createCartIfDontExist(utilisateur);
		if(commande == null) {
			return null;
		}
		
		commande.setStatus(StatusCommande.EN_COURS);
		List<CommandeMedicament> listCommandeMedicament = commande.getCommandeMedicaments();
		if(listCommandeMedicament.isEmpty()) {
			return null;
		}
		commande.setTotal(listCommandeMedicament.stream().mapToDouble(cm -> cm.getPresentation().getPrix() * cm.getQuantite()).sum());
		Livraison livraison = new Livraison();
		livraison.setCommande(commande);
		livraison.setDateLivraison(Instant.now());
		
		List<LivraisonPresentation> livraisonPresentations =createLivraisonMedicamentsEntity(inOneTime, listCommandeMedicament);
		
		livraison.setLivraisonPresentations(livraisonPresentations);
		livraison = livraisonComposant.saveLivraison(livraison);
		commande.getLivraisons().add(livraison);
		commande = commandeComposant.validateCart(commande);
		if(saveName != null && !saveName.isEmpty()) {
			saveAsCommandeType(saveName, utilisateur, commande);
		}
		createCartIfDontExist(utilisateur);
		
		return livraisonMapper.livraisontoLivraisonDTO(livraison, inOneTime.get());
	}
	
	private void saveAsCommandeType(String saveName, Utilisateur utilisateur, Commande commande) {
		CommandeType commandeType = new CommandeType();
		commandeType.setName(saveName);
		commandeType.setCommande(commande);
		commandeType.setUtilisateur(utilisateur);
		commandeComposant.saveCommandeType(commandeType);
	}
	
	/**
	 * @param utilisateur l'utilisateur dont on cherche le panier
	 * @return le panier de l'utilisateur s'il existe, sinon on en crée un nouveau
	 */
	public Commande createCartIfDontExist(Utilisateur utilisateur) {
		Commande commande = commandeComposant.getCart(utilisateur.getEmail());
		if(commande == null) {
			commande = new Commande();
			commande.setUtilisateur(utilisateur);
			commande.setStatus(StatusCommande.PANIER);
			commande.setDateCommande(Instant.now());
			commande = commandeComposant.createNewCommande(commande);
		}
		return commande;
	}
	
	/**
	 * @param quantite  la quantité de la présentation à ajouter au panier
	 * @param presentation la présentation à ajouter au panier
	 * @param commande le panier dans lequel on ajoute la présentation
	 */
	public void addPresentationToCart(int quantite, Presentation presentation, Commande commande) {
		CommandeMedicament commandeMedicament = commandeComposant.findFirstByNumeroCommandeAndCodeCIP7(commande.getNumeroCommande(), presentation.getCodeCIP7());
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
	}
	/**
	 * @param inOneTime le booléen qui indique si la livraison est possible en une fois ou non
	 * @param listCommandeMedicament la liste des présentations commandées
	 * @return la liste des livraisons de présentations
	 */
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
			presentationComposant.updatePresentation(presentation);
		});
		return livraisonPresentations;
	}
	
	public CommandeDetailDTO getDetailCommande(String email, int idCommande) {
		Utilisateur utilisateur = utilisateurComposant.getUserByEmail(email);
		if(utilisateur == null) {
			return null;
		}
		Commande commande = utilisateur.getCommandes().stream().filter(c -> c.getNumeroCommande() == idCommande).findFirst().orElse(null);
		if(commande == null) {
			return null;
		}
		List<PresentationRecapCommandeDTO> presentationRecapCommandeDTOs = commande.getCommandeMedicaments().stream()
				.map(commandeMapper::commandeMedicamentToPresentationRecapCommandeDTO).toList();
		List<LivraisonDetailDTO> livraisonDetailDTOs = commande.getLivraisons().stream()
				.map(livraison  -> {
						List<PresentationRecapCommandeDTO> recapLivraisonDTO = livraison.getLivraisonPresentations().stream()
								.map(livraisonMapper::livraisonPresentationToPresentationRecapCommandeDTO).toList();
						return livraisonMapper.livraisonToLivraisonDetailDTO(livraison,recapLivraisonDTO);
				}).toList();
		return commandeMapper.commandeToCommandeDetailDTO(commande,commande.getStatus() == StatusCommande.LIVREE,livraisonDetailDTOs,presentationRecapCommandeDTOs);
	}
}
