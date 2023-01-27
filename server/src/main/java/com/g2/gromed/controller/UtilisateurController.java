package com.g2.gromed.controller;

import com.g2.gromed.endpoint.IUtilisateurEndpoint;
import com.g2.gromed.model.dto.utilisateur.UtilisateurDTO;
import com.g2.gromed.service.UtilisateurService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@AllArgsConstructor
public class UtilisateurController implements IUtilisateurEndpoint {
	private UtilisateurService utilisateurService;
	@Override
	public ResponseEntity<UtilisateurDTO> authenticate(String email, String motDePasse) {
		UtilisateurDTO utilisateur = utilisateurService.authenticate(email, motDePasse);
		return utilisateur ==null ? ResponseEntity.notFound().build() : ResponseEntity.ok(utilisateur) ;
	}
}
