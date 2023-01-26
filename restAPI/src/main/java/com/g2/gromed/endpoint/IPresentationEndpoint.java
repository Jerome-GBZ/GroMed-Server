package com.g2.gromed.endpoint;


import com.g2.gromed.model.dto.presentation.Pagination;
import com.g2.gromed.model.dto.presentation.PresentationCardDTO;
import com.g2.gromed.model.dto.presentation.PresentationDetailDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/presentation")
public interface IPresentationEndpoint {

    @ApiOperation(value = "Récupère toutes les présentations des différents médicaments")
	@ApiResponse(responseCode = "200", description = "OK")
	@ApiResponse(responseCode = "500", description = "Erreur serveur")
	@ApiResponse(responseCode = "404", description = "Médicament non trouvé")
    @GetMapping(value = "/all", produces = "application/json")
    ResponseEntity<Page<PresentationCardDTO>> getPresentations(@ModelAttribute("pagination") Pagination pagination);

	@ApiOperation(value = "Récupère les informations détallées d'une présentation d'un médicament")
	@ApiResponse(responseCode = "200", description = "OK")
	@ApiResponse(responseCode = "500", description = "Erreur serveur")
	@ApiResponse(responseCode = "404", description = "Médicament non trouvé")
	@GetMapping(value = "/detail", produces = "application/json")
	ResponseEntity<PresentationDetailDTO> getDetailPresentation(@RequestParam() String codeCIP7);
}
