package com.g2.gromed.endpoint;


import com.g2.gromed.model.Filtre;
import com.g2.gromed.model.Pagination;
import com.g2.gromed.model.dto.PresentationCardDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/presentation")
public interface IPresentationEndpoint {

    @ApiOperation(value = "Récupère toutes les présentations des différents médicaments",
            response = PresentationCardDTO.class)
	@ApiResponse(responseCode = "200", description = "OK",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = PresentationCardDTO.class)))
	@ApiResponse(responseCode = "500", description = "Erreur serveur",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = PresentationCardDTO.class)))
	@ApiResponse(responseCode = "404", description = "Médicament non trouvé",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = PresentationCardDTO.class)))
    @GetMapping("/all")
    ResponseEntity<Page<PresentationCardDTO>> getAllPresentations(@ModelAttribute("pagination") Pagination pagination);
}
