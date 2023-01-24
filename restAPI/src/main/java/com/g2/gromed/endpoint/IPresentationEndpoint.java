package com.g2.gromed.endpoint;


import com.g2.gromed.dto.PresentationCardDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/presentation")
public interface IPresentationEndpoint {
	
	@ApiOperation(value = "Récupère toutes les présentations d'un médicament",
			response = PresentationCardDTO.class,
			responseContainer = "Page")
	@ApiResponses({
			@ApiResponse(code = 200, message = "OK", response = PresentationCardDTO.class, responseContainer = "Page"),
			@ApiResponse(code = 500, message = "Erreur serveur", response = PresentationCardDTO.class, responseContainer = "Page"),
			@ApiResponse(code = 404, message = "Médicament non trouvé", response = PresentationCardDTO.class, responseContainer = "Page")}
	)
	@GetMapping("/all")
	ResponseEntity<Page<PresentationCardDTO>> getAllPresentations();
}
