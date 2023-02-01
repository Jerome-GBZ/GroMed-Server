package com.g2.gromed.endpoint;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
public interface IAdminEndpoint {
	
	@ApiOperation(value = "Ajouter une présentation au panier dans une certaine quantité")
	@ApiResponse(responseCode = "200", description = "OK")
	@ApiResponse(responseCode = "500", description = "Erreur serveur")
	@PostMapping(value = "/refill", produces = "application/json")
	ResponseEntity<Boolean> refill();
	
}
