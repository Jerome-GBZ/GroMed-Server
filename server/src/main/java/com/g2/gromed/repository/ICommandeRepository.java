package com.g2.gromed.repository;

import com.g2.gromed.entity.Commande;
import com.g2.gromed.entity.StatusCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommandeRepository extends JpaRepository<Commande, Long> {
	Commande findFirstByStatusAndUtilisateurEmail(StatusCommande panier, String email);

	@Query("select c from Commande c join c.utilisateur u where u.email = :email and c.status in :status order by c.dateCommande desc")
	List<Commande> findByEmailAndStatusIn(String email, List<StatusCommande> status);
}
