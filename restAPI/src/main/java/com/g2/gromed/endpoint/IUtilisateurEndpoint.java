package com.g2.gromed.endpoint;

import com.g2.gromed.model.dto.utilisateur.UtilisateurDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/utilisateur")
public interface IUtilisateurEndpoint {

    @ApiOperation(value = "Récupère les informations d'un utilisateur si les identifiants sont corrects")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "500", description = "Erreur serveur")
    @ApiResponse(responseCode = "404", description = "Identifiant incorrect")
    @PostMapping(value = "/connection", produces = "application/json")
    ResponseEntity<UtilisateurDTO> authenticate(@RequestParam() String email, @RequestParam() String motDePasse);
}
