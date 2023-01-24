package com.g2.gromed.endpoint;

import com.g2.gromed.model.dto.commande.PresentationPanierDTO;
import com.g2.gromed.model.dto.presentation.PresentationCardDTO;
import com.g2.gromed.model.dto.utilisateur.UtilisateurDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/commande")
public interface ICommandeEndpoint {
	
	@ApiOperation(value = "Ajouter une présentation au panier dans une certaine quantité",
			response = PresentationCardDTO.class)
	@ApiResponse(responseCode = "200", description = "OK",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = PresentationPanierDTO.class)))
	@ApiResponse(responseCode = "500", description = "Erreur serveur",
			content = @Content(mediaType = "application/json"))
	@ApiResponse(responseCode = "404", description = "Commande ou présentation non trouvé",
			content = @Content(mediaType = "application/json"))
	@PostMapping("/add")
	ResponseEntity<List<PresentationPanierDTO>> addPresentationAuPanier(@RequestParam("idUtilisateur") Long idUtilisateur, @RequestParam("idPresentation") Long idPresentation, @RequestParam("quantite") int quantite);
	
	@ApiOperation(value = "Supprimer un produit du panier",
			response = PresentationCardDTO.class)
	@ApiResponse(responseCode = "200", description = "OK",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = PresentationPanierDTO.class)))
	@ApiResponse(responseCode = "500", description = "Erreur serveur",
			content = @Content(mediaType = "application/json"))
	@ApiResponse(responseCode = "404", description = "Commande ou présentation non trouvé dans la commande",
			content = @Content(mediaType = "application/json"))
	@DeleteMapping("/delete")
	ResponseEntity<List<PresentationPanierDTO>> deletePresentationDuPanier(@RequestParam("idUtilisateur") Long idUtilisateur,@RequestParam("idPresentation") Long idPresentation);
	
	@ApiOperation(value = "Récupérer le panier de l'utilisateur",
			response = PresentationCardDTO.class)
	@ApiResponse(responseCode = "200", description = "OK",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = PresentationPanierDTO.class)))
	@ApiResponse(responseCode = "500", description = "Erreur serveur",
			content = @Content(mediaType = "application/json"))
	@ApiResponse(responseCode = "404", description = "Commande de l'utilisateur non trouvé",
			content = @Content(mediaType = "application/json"))
	@GetMapping("/panier")
	ResponseEntity<List<PresentationPanierDTO>> getPanierUtilisateur(@RequestParam("idUtilisateur") Long idUtilisateur);
	
}
