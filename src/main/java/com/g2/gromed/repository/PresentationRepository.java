package com.g2.gromed.repository;

import com.g2.gromed.entity.Presentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface PresentationRepository extends JpaRepository<Presentation, Long> {
	
	
	Page<Presentation> findAllPresentation(Pageable pagination);
	
}
