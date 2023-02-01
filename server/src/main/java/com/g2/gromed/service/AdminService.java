package com.g2.gromed.service;

import com.g2.gromed.composant.CommandeComposant;
import com.g2.gromed.composant.LivraisonComposant;
import com.g2.gromed.composant.PresentationComposant;
import com.g2.gromed.entity.*;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Log
@Service
@AllArgsConstructor
public class AdminService {
	private PresentationComposant presentationComposant;
	private LivraisonComposant livraisonComposant;
	
	private CommandeComposant commandeComposant;
	public boolean refill() {
		List<Presentation> presentations = presentationComposant.getPresentationWithoutStock();
		presentations.forEach(presentation -> {
			presentation.setStock(100);
		});
		List<Commande> commandes = commandeComposant.getCommandesNotDelivered();
		commandes.forEach(this::sendLastDelivery);
		commandeComposant.saveAll(commandes);
		return true;
	}
	
	private void sendLastDelivery(Commande commande) {
		Clock cl = Clock.systemDefaultZone();
		List<CommandeMedicament> commandeMedicaments = commande.getCommandeMedicaments();
		List<LivraisonPresentation> livraisonPresentations = commande.getLivraisons().get(0).getLivraisonPresentations();
		List<LivraisonPresentation> newLivraison = getLivraisonPresentations(commandeMedicaments, livraisonPresentations);
		Livraison livraison = new Livraison();
		livraison.setLivraisonPresentations(newLivraison);
		livraison.setCommande(commande);
		livraison.setDateLivraison(cl.instant());
		livraisonComposant.saveLivraison(livraison);
		commande.setStatus(StatusCommande.LIVREE);
		commande.setDateCommande(cl.instant());
	}
	
	private List<LivraisonPresentation> getLivraisonPresentations(List<CommandeMedicament> commandeMedicaments, List<LivraisonPresentation> livraisonPresentations) {
		List<LivraisonPresentation> newLivraison = new ArrayList<>();
		HashMap<String,Integer> quantity = new HashMap<>();
		commandeMedicaments.forEach(commandeMedicament -> {
			quantity.put(commandeMedicament.getPresentation().getCodeCIP7(), commandeMedicament.getQuantite());
		});
		livraisonPresentations.forEach(livraisonPresentation -> {
			quantity.put(livraisonPresentation.getPresentation().getCodeCIP7(),quantity.get(livraisonPresentation.getPresentation().getCodeCIP7())-livraisonPresentation.getQuantite());
		});
		commandeMedicaments.forEach(commandeMedicament -> {
			if(quantity.get(commandeMedicament.getPresentation().getCodeCIP7())>0) {
				LivraisonPresentation livraisonPresentation = new LivraisonPresentation();
				livraisonPresentation.setPresentation(commandeMedicament.getPresentation());
				livraisonPresentation.setQuantite(quantity.get(commandeMedicament.getPresentation().getCodeCIP7()));
				newLivraison.add(livraisonPresentation);
			}
		});
		return newLivraison;
	}
}
