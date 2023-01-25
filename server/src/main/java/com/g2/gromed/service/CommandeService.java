package com.g2.gromed.service;

import com.g2.gromed.composant.CommandeComposant;
import com.g2.gromed.composant.PresentationComposant;
import com.g2.gromed.composant.UtilisateurComposant;
import com.g2.gromed.entity.Commande;
import com.g2.gromed.entity.CommandeMedicament;
import com.g2.gromed.entity.Presentation;
import com.g2.gromed.entity.Utilisateur;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Log
@Service
public class CommandeService {
	private CommandeComposant commandeComposant;
	
	private UtilisateurComposant utilisateurComposant;
	
	private PresentationComposant presentationComposant;
	
	public void insertMedicamentDansPanier(Long idUtilisateur, String codeCIP7, int quantite) {
		if(!utilisateurComposant.existById(idUtilisateur)){
			return;
		}
		Commande commande = commandeComposant.getPanierEnCours(idUtilisateur);
		Presentation presentation = presentationComposant.getPresentationByCodeCIP7(codeCIP7);
		Long id = null;
		CommandeMedicament commandeMedicament = commandeComposant.findFirstByNumeroCommandeAndCodeCIP7(commande.getNumeroCommande(), presentation.getPresentationId());
		if(commandeMedicament == null){
			CommandeMedicament newMedicament = new CommandeMedicament();
			newMedicament.setQuantite(quantite);
			newMedicament.setCommande(commande);
			newMedicament.setPresentation(presentation);
			id = commandeComposant.save(newMedicament);
		}else{
			commandeMedicament.setQuantite(commandeMedicament.getQuantite() + quantite);
			id = commandeComposant.save(commandeMedicament);
		}
		log.info("insertMedicamentDansPanier: " + id);
	}
}
