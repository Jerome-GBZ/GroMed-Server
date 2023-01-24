package com.g2.gromed.endpoint;

import com.g2.gromed.model.dto.presentation.PresentationCardDTO;
import com.g2.gromed.model.dto.utilisateur.UtilisateurDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin(origins = {"http://localhost:4200","http://129.88.210.54:4200"})
@RequestMapping("/utilisateur")
public interface IUtilisateurEndpoint {
	
	@ApiOperation(value = "Récupère les informations d'un utilisateur si les identifiants sont corrects",
			response = PresentationCardDTO.class)
	@ApiResponse(responseCode = "200", description = "OK",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = UtilisateurDTO.class)))
	@ApiResponse(responseCode = "500", description = "Erreur serveur",
			content = @Content(mediaType = "application/json"))
	@ApiResponse(responseCode = "404", description = "Médicament non trouvé",
			content = @Content(mediaType = "application/json"))
	@GetMapping("/connection")
	ResponseEntity<UtilisateurDTO> connection(@RequestParam() String email, @RequestParam() String motDePasse);
}
