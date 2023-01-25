package com.g2.gromed.repository;

import com.g2.gromed.entity.Presentation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Repository
public interface IPresentationRepository extends JpaRepository<Presentation, Long> {
	Presentation findFirstByCodeCIP7(String codeCIP7);
	
}
