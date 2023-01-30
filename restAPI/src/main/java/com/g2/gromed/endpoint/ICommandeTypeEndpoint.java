package com.g2.gromed.endpoint;

import com.g2.gromed.model.dto.commande.PresentationRecapCommandeDTO;
import com.g2.gromed.model.dto.commandetype.CommandeTypeInfo;
import com.g2.gromed.model.dto.utilisateur.UtilisateurDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/commandetype")
public interface ICommandeTypeEndpoint {
	
	@ApiOperation(value = "Récupère la liste des CommandeType")
	@ApiResponse(responseCode = "200", description = "OK")
	@ApiResponse(responseCode = "500", description = "Erreur serveur")
	@ApiResponse(responseCode = "404", description = "CommandeType non trouvé")
	@GetMapping(value = "/all", produces = "application/json")
	ResponseEntity<List<CommandeTypeInfo>> getCommandeTypes(@RequestParam("email") String email, @RequestParam("search") String search);
	
	@ApiOperation(value = "Récupère la liste des CommandeType")
	@ApiResponse(responseCode = "200", description = "OK")
	@ApiResponse(responseCode = "500", description = "Erreur serveur")
	@ApiResponse(responseCode = "404", description = "CommandeType non trouvé")
	@GetMapping(value = "/detail", produces = "application/json")
	ResponseEntity<List<PresentationRecapCommandeDTO>> getCommandeTypeDetail(@RequestParam("email") String email, @RequestParam("name") String name);
	
	@ApiOperation(value = "Récupère la liste des CommandeType")
	@ApiResponse(responseCode = "200", description = "OK")
	@ApiResponse(responseCode = "500", description = "Erreur serveur")
	@ApiResponse(responseCode = "404", description = "CommandeType non trouvé")
	@GetMapping(value = "/addtocart", produces = "application/json")
	ResponseEntity<UtilisateurDTO> addCommandeTypeToUserCart(@RequestParam("email") String email, @RequestParam("name") String name);
}
