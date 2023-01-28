package com.g2.gromed.composant;

import com.g2.gromed.entity.*;
import com.g2.gromed.repository.ICommandeMedicamentRepository;
import com.g2.gromed.repository.ICommandeRepository;
import com.g2.gromed.repository.ILivraisonRepository;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

import org.hibernate.mapping.Array;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Log
@Component
@AllArgsConstructor
public class CommandeComposant {
	private ICommandeRepository commandeRepository;
	
	private ICommandeMedicamentRepository commandeMedicamentRepository;
	
	public Commande getCart(String email) {
  	return commandeRepository.findFirstByStatusAndUtilisateurEmail(StatusCommande.PANIER,email);
	}
	
	public CommandeMedicament findFirstByNumeroCommandeAndCodeCIP7(Long numeroCommande, String codeCIP7) {
		return commandeMedicamentRepository.findFirstByCommandeNumeroCommandeAndPresentationCodeCIP7(numeroCommande, codeCIP7);
	}
	
	public void addToCart(CommandeMedicament commandeMedicament) {
		commandeMedicamentRepository.save(commandeMedicament);
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
	
	public void validateCart(Commande commande) {
		commandeRepository.save(commande);
	}

    public List<Commande> getAllByEmail(String email) {
		List<StatusCommande> statusList = Arrays.asList(StatusCommande.LIVREE, StatusCommande.EN_COURS);
        return commandeRepository.findByEmailAndStatusIn(email, statusList);
    }
}
