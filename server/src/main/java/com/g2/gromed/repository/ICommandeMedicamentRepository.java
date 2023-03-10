package com.g2.gromed.repository;

import com.g2.gromed.entity.CommandeMedicament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICommandeMedicamentRepository extends JpaRepository<CommandeMedicament, Long> {
	
	CommandeMedicament findFirstByCommandeNumeroCommandeAndPresentationCodeCIP7(Long numeroCommande, String codeCIP7);
	
	int countByCommandeNumeroCommande(Long numeroCommande);
}
