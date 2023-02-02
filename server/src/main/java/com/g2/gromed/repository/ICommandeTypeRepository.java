package com.g2.gromed.repository;

import com.g2.gromed.entity.CommandeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommandeTypeRepository extends JpaRepository<CommandeType, Long> {
	
	
	CommandeType findFirstByUtilisateurEmailAndName(String email, String name);
	
	List<CommandeType> findByUtilisateurEmailAndNameContaining(String email, String search);
}

