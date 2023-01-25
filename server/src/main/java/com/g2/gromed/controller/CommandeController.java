package com.g2.gromed.controller;

import com.g2.gromed.endpoint.ICommandeEndpoint;
import com.g2.gromed.model.dto.commande.PresentationPanierDTO;
import com.g2.gromed.service.CommandeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class CommandeController implements ICommandeEndpoint {
	private CommandeService commandeService;
	@Override
	public ResponseEntity<Integer> addPresentationToCart(Long idUtilisateur, String codeCIP7, int quantite) {
		Integer cartItemCount = commandeService.addPresentationToCart(idUtilisateur, codeCIP7, quantite);
		return cartItemCount!= null ? ResponseEntity.ok(cartItemCount) : ResponseEntity.notFound().build();
	}
	
	@Override
	public ResponseEntity<Integer> deletePresentationToCart(Long idUtilisateur, String codeCIP7) {
		Integer cartItemCount = commandeService.deletePresentationToCart(idUtilisateur, codeCIP7);
		return cartItemCount!= null ? ResponseEntity.ok(cartItemCount) : ResponseEntity.notFound().build();
	}
	
	@Override
	public ResponseEntity<List<PresentationPanierDTO>> getUserCart(Long idUtilisateur) {
		List<PresentationPanierDTO> cartItemCount = commandeService.getCart(idUtilisateur);
		return cartItemCount!= null ? ResponseEntity.ok(cartItemCount) : ResponseEntity.notFound().build();
	}
}
