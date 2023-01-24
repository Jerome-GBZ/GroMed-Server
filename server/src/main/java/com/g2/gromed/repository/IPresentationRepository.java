package com.g2.gromed.repository;

import com.g2.gromed.entity.Presentation;
import com.g2.gromed.model.dto.presentation.PresentationDetailDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface IPresentationRepository extends JpaRepository<Presentation, String> {
	Presentation findFirstByCodeCIP7(String codeCIP7);
	
}
