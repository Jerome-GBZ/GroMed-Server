package com.g2.gromed.service;

import com.g2.gromed.composant.UtilisateurComposant;
import com.g2.gromed.entity.Utilisateur;
import com.g2.gromed.mapper.IUtilisateurMapper;
import com.g2.gromed.model.dto.utilisateur.UtilisateurDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UtilisateurService {
	
	private UtilisateurComposant utilisateurComposant;
	private IUtilisateurMapper utilisateurMapper;
	
	public UtilisateurDTO connectionUtilisateur(String email, String motDePasse){
		Utilisateur utilisateur = utilisateurComposant.connectionUtilisateur(email, motDePasse);
		return utilisateurMapper.utilisateurToUtilisateurDTO(utilisateur);
	}
}
