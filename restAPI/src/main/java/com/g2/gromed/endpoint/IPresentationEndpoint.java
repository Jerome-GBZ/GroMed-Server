package com.g2.gromed.endpoint;


import com.g2.gromed.model.dto.filtre.FiltreDTO;
import com.g2.gromed.model.dto.presentation.PresentationCardDTO;
import com.g2.gromed.model.dto.presentation.PresentationDetailDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/presentation")
public interface IPresentationEndpoint {

    @ApiOperation(value = "Récupère une page de présentations de médicaments, filtrées selon des critères de recherche")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "500", description = "Erreur serveur")
    @GetMapping(value = "/all", produces = "application/json")
    ResponseEntity<Page<PresentationCardDTO>> getPresentations(@Parameter Pageable pagination, @Parameter FiltreDTO filtreDTO);

    @ApiOperation(value = "Récupère les informations détaillées d'une présentation d'un médicament")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "500", description = "Erreur serveur")
    @ApiResponse(responseCode = "404", description = "Médicament non trouvé")
    @GetMapping(value = "/detail", produces = "application/json")
    ResponseEntity<PresentationDetailDTO> getDetailPresentation(@RequestParam() String codeCIP7);
}
