package com.g2.gromed.endpoint;

import com.g2.gromed.model.dto.filtre.FiltreDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/filtre")
public interface IFiltreEndpoint {

    @ApiOperation(value = "Récupère tous les filtres pour les présentations")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "500", description = "Erreur serveur")
    @ApiResponse(responseCode = "404", description = "Filtres introuvables")
    @PostMapping(value = "/all", produces = "application/json")
    ResponseEntity<FiltreDTO> getFiltres();
}
