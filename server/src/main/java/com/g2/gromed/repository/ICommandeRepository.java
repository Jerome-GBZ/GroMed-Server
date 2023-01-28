package com.g2.gromed.repository;

import com.g2.gromed.entity.Commande;
import com.g2.gromed.entity.StatusCommande;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICommandeRepository extends JpaRepository<Commande, Long> {
	Commande findFirstByStatusAndUtilisateurEmail(StatusCommande panier, String email);

	@Query("select c from Commande c join c.utilisateur u where u.email = :email and c.status in :status")
	List<Commande> findByEmailAndStatusIn(String email, List<StatusCommande> status);
}
