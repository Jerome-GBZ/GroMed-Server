package com.g2.gromed.service;

import com.g2.gromed.composant.CommandeComposant;
import com.g2.gromed.composant.UtilisateurComposant;
import com.g2.gromed.entity.Commande;
import com.g2.gromed.entity.Utilisateur;
import com.g2.gromed.mapper.IUtilisateurMapper;
import com.g2.gromed.model.dto.utilisateur.UtilisateurDTO;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Log
@Service
@AllArgsConstructor
public class UtilisateurService {

    private UtilisateurComposant utilisateurComposant;
    private IUtilisateurMapper utilisateurMapper;
    private CommandeComposant commandeComposant;
    private CommandeService commandeService;

    public UtilisateurDTO authenticate(String email, String motDePasse) {
        Utilisateur utilisateur = utilisateurComposant.authenticate(email, motDePasse);
        Commande commande = commandeService.createCartIfDontExist(utilisateur);
        return utilisateurMapper.toUtilisateurDTO(utilisateur, commandeComposant.countCartPresentation(commande.getNumeroCommande()));
    }


}
