package com.g2.gromed.service;

import com.g2.gromed.composant.CommandeComposant;
import com.g2.gromed.composant.CommandeTypeComposant;
import com.g2.gromed.composant.UtilisateurComposant;
import com.g2.gromed.entity.Commande;
import com.g2.gromed.entity.CommandeType;
import com.g2.gromed.entity.Utilisateur;
import com.g2.gromed.mapper.ICommandeMapper;
import com.g2.gromed.mapper.ICommandeTypeMapper;
import com.g2.gromed.mapper.IUtilisateurMapper;
import com.g2.gromed.model.dto.commande.PresentationRecapCommandeDTO;
import com.g2.gromed.model.dto.commandetype.CommandeTypeInfo;
import com.g2.gromed.model.dto.utilisateur.UtilisateurDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommandeTypeService {
	private CommandeTypeComposant commandeTypeComposant;
	private CommandeComposant commandeComposant;
	private ICommandeTypeMapper commandeTypeMapper;
	private CommandeService commandeService;
	private ICommandeMapper commandeMapper;
	private UtilisateurComposant utilisateurComposant;
	private IUtilisateurMapper utilisateurMapper;
	
	public List<CommandeTypeInfo> getCommandeTypes(String email, String search) {
		List<CommandeType> commandeTypes = commandeTypeComposant.getCommandeTypes(email, search);
		return commandeTypes.stream().map(commandeTypeMapper::toCommandeTypeInfo).toList();
	}
	
	public List<PresentationRecapCommandeDTO> getCommandeTypeDetail(String email, String name) {
		CommandeType commandeType = commandeTypeComposant.getCommandeType(email, name);
		return commandeType.getCommande().getCommandeMedicaments().stream().map(commandeMapper::commandeMedicamentToPresentationRecapCommandeDTO).toList();
	}
	
	public UtilisateurDTO addCommandeTypeToUserCart(String email, String name) {
		Utilisateur utilisateur = utilisateurComposant.getUserByEmail(email);
		if(utilisateur == null) {
			return null;
		}
		Commande cart = commandeComposant.getCart(email);
		CommandeType commandeType = commandeTypeComposant.getCommandeType(email, name);
		commandeType.getCommande().getCommandeMedicaments().forEach(cm-> commandeService.addPresentationToCart(cm.getQuantite(), cm.getPresentation(),cart));
		int countCartItem = commandeComposant.getCart(email).getCommandeMedicaments().size();
		return utilisateurMapper.toUtilisateurDTO(utilisateur,countCartItem);
	}
}
