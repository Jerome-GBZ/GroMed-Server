package com.g2.gromed.controller;

import com.g2.gromed.endpoint.ICommandeTypeEndpoint;
import com.g2.gromed.model.dto.commande.PresentationRecapCommandeDTO;
import com.g2.gromed.model.dto.commandetype.CommandeTypeInfo;
import com.g2.gromed.model.dto.utilisateur.UtilisateurDTO;
import com.g2.gromed.service.CommandeTypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin // NOSONAR
@RestController
@AllArgsConstructor
public class CommandeTypeController implements ICommandeTypeEndpoint {
	private CommandeTypeService commandeTypeService;
	@Override
	public ResponseEntity<List<CommandeTypeInfo>> getCommandeTypes(String email, String search) {
		List<CommandeTypeInfo> commandeTypeInfos = commandeTypeService.getCommandeTypes(email, search);
		return commandeTypeInfos != null ? ResponseEntity.ok(commandeTypeInfos) : ResponseEntity.notFound().build();
	}
	
	@Override
	public ResponseEntity<List<PresentationRecapCommandeDTO>> getCommandeTypeDetail(String email, String name) {
		List<PresentationRecapCommandeDTO> presentationRecapCommandeDTOS = commandeTypeService.getCommandeTypeDetail(email, name);
		return presentationRecapCommandeDTOS != null ? ResponseEntity.ok(presentationRecapCommandeDTOS) : ResponseEntity.notFound().build();
	}
	
	@Override
	public ResponseEntity<UtilisateurDTO> addCommandeTypeToUserCart(String email, String name) {
		UtilisateurDTO utilisateurDTO = commandeTypeService.addCommandeTypeToUserCart(email, name);
		return utilisateurDTO != null ? ResponseEntity.ok(utilisateurDTO) : ResponseEntity.notFound().build();
	}
}
