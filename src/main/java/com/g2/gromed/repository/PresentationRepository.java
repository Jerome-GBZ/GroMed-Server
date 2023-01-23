package com.g2.gromed.repository;

import com.g2.gromed.entity.Presentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface PresentationRepository extends JpaRepository<Presentation, String> {
	
	Presentation findByCodeCIP7(String codeCIP7);
	@Query("SELECT P FROM Presentation P where P.codeCIP7 = '3014421'")
	Presentation findAllPresentation();
	
}
