package com.g2.gromed.endpoint;

import com.g2.gromed.model.dto.commande.*;
import com.g2.gromed.model.dto.utilisateur.UtilisateurDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/commande")
public interface ICommandeEndpoint {
	
	@ApiOperation(value = "Ajouter une présentation au panier dans une certaine quantité")
	@ApiResponse(responseCode = "200", description = "OK")
	@ApiResponse(responseCode = "500", description = "Erreur serveur")
	@ApiResponse(responseCode = "404", description = "Commande ou présentation non trouvé")
	@PostMapping(value = "/add", produces = "application/json")
	ResponseEntity<UtilisateurDTO> addPresentationToCart(@RequestParam("email") String email, @RequestParam("codeCIP7") String codeCIP7, @RequestParam("quantite") int quantite);
	
	@ApiOperation(value = "Supprimer un produit du panier")
	@ApiResponse(responseCode = "200", description = "OK")
	@ApiResponse(responseCode = "500", description = "Erreur serveur")
	@ApiResponse(responseCode = "404", description = "Commande ou présentation non trouvé dans la commande")
	@DeleteMapping(value = "/delete", produces = "application/json")
	ResponseEntity<UtilisateurDTO> deletePresentationToCart(@RequestParam("email") String email,@RequestParam("codeCIP7") String codeCIP7);
	
	@ApiOperation(value = "Récupérer le panier de l'utilisateur")
	@ApiResponse(responseCode = "200", description = "OK")
	@ApiResponse(responseCode = "500", description = "Erreur serveur")
	@ApiResponse(responseCode = "404", description = "Commande de l'utilisateur non trouvé")
	@GetMapping(value = "/panier", produces = "application/json")
	ResponseEntity<List<PresentationPanierDTO>> getUserCart(@RequestParam("email") String email);

	@ApiOperation(value = "Vérifier la disponibilité des médicaments au moment de la validation du panier")
	@ApiResponse(responseCode = "200", description = "OK")
	@ApiResponse(responseCode = "500", description = "Erreur serveur")
	@ApiResponse(responseCode = "404", description = "Panier non trouvé")
	@GetMapping(value = "/panier/disponibilite", produces = "application/json")
	ResponseEntity<AlerteIndisponibilitePresentationDTO> checkStockAvailability(@RequestParam("email") String email);
	
	@ApiOperation(value = "Valider le panier de l'utilisateur et renvoie la premiere livraison")
	@ApiResponse(responseCode = "200", description = "OK")
	@ApiResponse(responseCode = "500", description = "Erreur serveur")
	@ApiResponse(responseCode = "404", description = "Probleme lors de la validation")
	@GetMapping(value = "/validate", produces = "application/json")
	ResponseEntity<LivraisonDTO> validateCart(@RequestParam("email") String email,@RequestParam("saveName") String saveName);
	
	@ApiOperation(value = "Renvoie le détail d'une commande")
	@ApiResponse(responseCode = "200", description = "OK")
	@ApiResponse(responseCode = "500", description = "Erreur serveur")
	@ApiResponse(responseCode = "404", description = "Probleme lors de la récupération des détails de la commande")
	@GetMapping(value = "/detail", produces = "application/json")
	ResponseEntity<CommandeDetailDTO> getDetailCommande(@RequestParam("email") String email,@RequestParam("commande") int idCommande);

	@ApiOperation(value = "Récupérer toutes les commandes de l'utilisateur")
	@ApiResponse(responseCode = "200", description = "OK")
	@ApiResponse(responseCode = "500", description = "Erreur serveur")
	@ApiResponse(responseCode = "404", description = "Probleme lors de la validation")
	@GetMapping(value = "/all", produces = "application/json")
	ResponseEntity<List<CommandeDTO>> getAllCommande(@RequestParam("email") String email);
}
