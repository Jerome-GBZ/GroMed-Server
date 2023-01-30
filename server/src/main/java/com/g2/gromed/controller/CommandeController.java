package com.g2.gromed.controller;

import com.g2.gromed.endpoint.ICommandeEndpoint;
import com.g2.gromed.model.dto.commande.*;
import com.g2.gromed.model.dto.utilisateur.UtilisateurDTO;
import com.g2.gromed.service.CommandeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@AllArgsConstructor
public class CommandeController implements ICommandeEndpoint {
	private CommandeService commandeService;
	@Override
	public ResponseEntity<UtilisateurDTO> addPresentationToCart(String email, String codeCIP7, int quantite) {
		UtilisateurDTO utilisateurDTO = commandeService.addPresentationToUserCart(email, codeCIP7, quantite);
		return utilisateurDTO!= null ? ResponseEntity.ok(utilisateurDTO) : ResponseEntity.notFound().build();
	}
	
	@Override
	public ResponseEntity<UtilisateurDTO> deletePresentationToCart(String email, String codeCIP7) {
		UtilisateurDTO utilisateurDTO = commandeService.deletePresentationFromCart(email, codeCIP7);
		return utilisateurDTO!= null ? ResponseEntity.ok(utilisateurDTO) : ResponseEntity.notFound().build();
	}
	
	@Override
	public ResponseEntity<List<PresentationPanierDTO>> getUserCart(String email) {
		List<PresentationPanierDTO> panier = commandeService.getCart(email);
		return panier!=null ? ResponseEntity.ok(panier) : ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<AlerteIndisponibilitePresentationDTO> checkStockAvailability(String email) {
		AlerteIndisponibilitePresentationDTO alerteIndisponibilitePresentationDTO = commandeService.getUnavailablePresentations(email);
		return alerteIndisponibilitePresentationDTO != null ? ResponseEntity.ok(alerteIndisponibilitePresentationDTO) : ResponseEntity.notFound().build();
	}
	
	@Override
	public ResponseEntity<LivraisonDTO> validateCart(String email,String saveName) {
		LivraisonDTO livraisonDTO = commandeService.validateCart(email,saveName);
		return livraisonDTO!= null ? ResponseEntity.ok(livraisonDTO) : ResponseEntity.notFound().build();
	}
	
	@Override
	public ResponseEntity<CommandeDetailDTO> getDetailCommande(String email, int idCommande) {
		CommandeDetailDTO commandeDetailDTO = commandeService.getDetailCommande(email, idCommande);
		return commandeDetailDTO!= null ? ResponseEntity.ok(commandeDetailDTO) : ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<List<CommandeDTO>> getAllCommande(String email) {
		List<CommandeDTO> commandesDTO = commandeService.getAllCommande(email);
		return commandesDTO != null ? ResponseEntity.ok(commandesDTO) : ResponseEntity.notFound().build();
	}
}
