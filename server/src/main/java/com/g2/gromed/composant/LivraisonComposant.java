package com.g2.gromed.composant;

import com.g2.gromed.entity.Livraison;
import com.g2.gromed.repository.ILivraisonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class LivraisonComposant {
	private ILivraisonRepository livraisonRepository;
	
	public Livraison saveLivraison(Livraison livraison) {
		return livraisonRepository.save(livraison);
	}
	
	public List<Livraison> getLivraisonsByCommande(long numeroCommande) {
		return livraisonRepository.findByCommandeNumeroCommande(numeroCommande);
	}
}
