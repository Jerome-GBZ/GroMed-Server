package com.g2.gromed.composant;

import com.g2.gromed.entity.Utilisateur;
import com.g2.gromed.repository.IUtilisateurRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UtilisateurComposant {
	private IUtilisateurRepository utilisateurRepository;
	
	public Utilisateur connectionUtilisateur(String email, String motDePasse){
		return utilisateurRepository.findFirstByEmailAndMotDePasse(email, motDePasse);
	}
	
	
	public boolean existById(Long idUtilisateur) {
		return utilisateurRepository.existsById(idUtilisateur);
	}
}
