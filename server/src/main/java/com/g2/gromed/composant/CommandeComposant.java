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
	
	public Commande getCart(Long idUtilisateur) {
		return commandeRepository.findFirstByUtilisateurAndStatus(idUtilisateur, StatusCommande.PANIER);
	}
	
	public CommandeMedicament findFirstByNumeroCommandeAndCodeCIP7(Long numeroCommande, String codeCIP7) {
		return commandeMedicamentRepository.findFirstByCommandeAndPresentation(numeroCommande, codeCIP7);
	}
	
	
	public Long save(CommandeMedicament commandeMedicament) {
		return commandeMedicamentRepository.save(commandeMedicament).getCommandeMedicamentId();
	}
}
