package com.g2.gromed.composant;

import com.g2.gromed.entity.CommandeType;
import com.g2.gromed.repository.ICommandeTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CommandeTypeComposant {
    private ICommandeTypeRepository commandeTypeRepository;

    public List<CommandeType> getCommandeTypes(String email, String search) {
        return commandeTypeRepository.findByUtilisateurEmailAndNameContaining(email, search);
    }

    public CommandeType getCommandeType(String email, String name) {
        return commandeTypeRepository.findFirstByUtilisateurEmailAndName(email, name);
    }
}
