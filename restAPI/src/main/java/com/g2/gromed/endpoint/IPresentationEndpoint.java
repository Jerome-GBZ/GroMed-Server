package com.g2.gromed.endpoint;


import com.g2.gromed.model.dto.presentation.Pagination;
import com.g2.gromed.model.dto.presentation.PresentationCardDTO;
import com.g2.gromed.model.dto.presentation.PresentationDetailDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/presentation")
public interface IPresentationEndpoint {

    @ApiOperation(value = "Récupère toutes les présentations des différents médicaments",
            response = PresentationCardDTO.class)
	@ApiResponse(responseCode = "200", description = "OK",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = PresentationCardDTO.class)))
	@ApiResponse(responseCode = "500", description = "Erreur serveur",
			content = @Content(mediaType = "application/json"))
	@ApiResponse(responseCode = "404", description = "Médicament non trouvé",
			content = @Content(mediaType = "application/json"))
    @GetMapping("/all")
    ResponseEntity<Page<PresentationCardDTO>> getLesPresentations(@RequestParam() Pagination pagination);
	
	
	@ApiOperation(value = "Récupère les informations détallées d'une présentation d'un médicament",
			response = PresentationDetailDTO.class)
	@ApiResponse(responseCode = "200", description = "OK",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = PresentationDetailDTO.class)))
	@ApiResponse(responseCode = "500", description = "Erreur serveur",
			content = @Content(mediaType = "application/json"))
	@ApiResponse(responseCode = "404", description = "Médicament non trouvé",
			content = @Content(mediaType = "application/json"))
	@GetMapping("/detail")
	ResponseEntity<PresentationDetailDTO> getDetailPresentation(@RequestParam() String codeCIP7);
}
