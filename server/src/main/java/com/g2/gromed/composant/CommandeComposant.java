package com.g2.gromed.composant;

import com.g2.gromed.entity.Commande;
import com.g2.gromed.entity.CommandeMedicament;
import com.g2.gromed.entity.CommandeType;
import com.g2.gromed.entity.StatusCommande;
import com.g2.gromed.repository.ICommandeMedicamentRepository;
import com.g2.gromed.repository.ICommandeRepository;
import com.g2.gromed.repository.ICommandeTypeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Log
@Component
@AllArgsConstructor
public class CommandeComposant {
    private ICommandeRepository commandeRepository;

    private ICommandeMedicamentRepository commandeMedicamentRepository;
    private ICommandeTypeRepository commandeTypeRepository;

    public Commande getCart(String email) {
        return commandeRepository.findFirstByStatusAndUtilisateurEmail(StatusCommande.PANIER, email);
    }

    public CommandeMedicament findFirstByNumeroCommandeAndCodeCIP7(Long numeroCommande, String codeCIP7) {
        return commandeMedicamentRepository.findFirstByCommandeNumeroCommandeAndPresentationCodeCIP7(numeroCommande, codeCIP7);
    }

    public void addToCart(CommandeMedicament commandeMedicament) {
        commandeMedicamentRepository.save(commandeMedicament);
    }

    public int countCartPresentation(Long numeroCommande) {
        return commandeMedicamentRepository.countByCommandeNumeroCommande(numeroCommande);
    }

    public Commande createNewCommande(Commande commande) {
        return commandeRepository.save(commande);
    }

    public void removeFromCart(CommandeMedicament commandePresentation) {
        commandeMedicamentRepository.delete(commandePresentation);
    }

    public Commande validateCart(Commande commande) {
        return commandeRepository.save(commande);
    }

    public List<Commande> getAllByEmail(String email) {
        List<StatusCommande> statusList = Arrays.asList(StatusCommande.LIVREE, StatusCommande.EN_COURS);
        return commandeRepository.findByEmailAndStatusIn(email, statusList);
    }

    public void saveCommandeType(CommandeType commandeType) {
        commandeTypeRepository.save(commandeType);
    }

    public List<Commande> getCommandesNotDelivered() {
        return commandeRepository.findByStatus(StatusCommande.EN_COURS);
    }

    public void saveAll(List<Commande> commandes) {
        commandeRepository.saveAll(commandes);
    }
}
