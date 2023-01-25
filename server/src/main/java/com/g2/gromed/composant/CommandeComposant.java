package com.g2.gromed.composant;

import com.g2.gromed.entity.Commande;
import com.g2.gromed.entity.CommandeMedicament;
import com.g2.gromed.entity.Presentation;
import com.g2.gromed.entity.StatusCommande;
import com.g2.gromed.repository.ICommandeMedicamentRepository;
import com.g2.gromed.repository.ICommandeRepository;
import com.g2.gromed.repository.ICommandeTypeRepository;
import com.g2.gromed.repository.IPresentationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@Log
@Component
@AllArgsConstructor
public class CommandeComposant {
	private ICommandeRepository commandeRepository;
	
	private ICommandeMedicamentRepository commandeMedicamentRepository;
	
	public Commande getPanierEnCours(Long idUtilisateur) {
		return commandeRepository.findFirstByUtilisateurAndStatus(idUtilisateur, StatusCommande.PANIER);
	}
	
	public CommandeMedicament findFirstByNumeroCommandeAndCodeCIP7(Long numeroCommande, Long idPresentation) {
		return commandeMedicamentRepository.findFirstByCommandeAndPresentation(numeroCommande, idPresentation);
	}
	
	
	public Long save(CommandeMedicament commandeMedicament) {
		return commandeMedicamentRepository.save(commandeMedicament).getCommandeMedicamentId();
	}
}
