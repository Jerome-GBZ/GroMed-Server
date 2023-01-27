package com.g2.gromed.repository;

import com.g2.gromed.entity.Livraison;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILivraisonRepository extends JpaRepository<Livraison,Long> {
}
