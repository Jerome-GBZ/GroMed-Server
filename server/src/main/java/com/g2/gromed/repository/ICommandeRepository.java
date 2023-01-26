package com.g2.gromed.repository;

import com.g2.gromed.entity.Commande;
import com.g2.gromed.entity.StatusCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICommandeRepository extends JpaRepository<Commande, Long> {

	Commande findFirstByStatusAndUtilisateurEmail(StatusCommande panier,String email);
}
