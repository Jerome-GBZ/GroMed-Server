package com.g2.gromed.repository;

import com.g2.gromed.entity.LivraisonPresentation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivraisonPresentationRepository extends JpaRepository<LivraisonPresentation, Long> {

}
