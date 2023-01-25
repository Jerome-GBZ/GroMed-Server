package com.g2.gromed.endpoint;

import com.g2.gromed.model.dto.filtre.FiltreDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/filtre")
public interface IFiltreEndpoint {

    @ApiOperation(value = "Récupère tout les filtres pour un médicament")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "500", description = "Erreur serveur")
    @ApiResponse(responseCode = "404", description = "Filtres introuvable")
    @PostMapping(value = "/all", produces = "application/json")
    ResponseEntity<FiltreDTO> getFiltres();
}
