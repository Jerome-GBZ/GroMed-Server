package com.g2.gromed.controller;

import com.g2.gromed.endpoint.IUtilisateurEndpoint;
import com.g2.gromed.model.dto.utilisateur.UtilisateurDTO;
import com.g2.gromed.service.UtilisateurService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UtilisateurController implements IUtilisateurEndpoint {
	private UtilisateurService utilisateurService;
	@Override
	public ResponseEntity<UtilisateurDTO> connection(String email, String motDePasse) {
		UtilisateurDTO utilisateur = utilisateurService.connectionUtilisateur(email, motDePasse);
		return utilisateur ==null ? ResponseEntity.notFound().build() : ResponseEntity.ok(utilisateur) ;
	}
}
