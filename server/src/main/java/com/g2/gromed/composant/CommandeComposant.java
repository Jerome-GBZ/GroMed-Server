package com.g2.gromed.composant;

import com.g2.gromed.entity.Commande;
import com.g2.gromed.entity.CommandeMedicament;
import com.g2.gromed.entity.StatusCommande;
import com.g2.gromed.repository.ICommandeMedicamentRepository;
import com.g2.gromed.repository.ICommandeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@Log
@Component
@AllArgsConstructor
public class CommandeComposant {
	private ICommandeRepository commandeRepository;
	
	private ICommandeMedicamentRepository commandeMedicamentRepository;
	
	public Commande getCart(String email) {
		return commandeRepository.findFirstByUtilisateurEmailAndStatus(email, StatusCommande.PANIER);
	}
	
	public CommandeMedicament findFirstByNumeroCommandeAndCodeCIP7(Long numeroCommande, String codeCIP7) {
		return commandeMedicamentRepository.findFirstByCommandeNumeroCommandeAndPresentationCodeCIP7(numeroCommande, codeCIP7);
	}
	
	public Long addToCart(CommandeMedicament commandeMedicament) {
		return commandeMedicamentRepository.save(commandeMedicament).getCommandeMedicamentId();
	}
	
	public int countCartPresentation(Long numeroCommande) {
		return commandeMedicamentRepository.countByCommandeNumeroCommande(numeroCommande);
	}
	
	public Commande createNewCommande(Commande commande) {
		return commandeRepository.save(commande);
	}
	
	public void removeFromCart(CommandeMedicament commandePresentation) {
		commandeMedicamentRepository.delete(commandePresentation);
	}
}
