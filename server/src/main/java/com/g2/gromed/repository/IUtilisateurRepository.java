package com.g2.gromed.repository;

import com.g2.gromed.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUtilisateurRepository extends JpaRepository<Utilisateur, String> {
	
	Utilisateur findFirstByEmailAndMotDePasse(String email, String motDePasse);

    Utilisateur findFirstByEmail(String email);
}
