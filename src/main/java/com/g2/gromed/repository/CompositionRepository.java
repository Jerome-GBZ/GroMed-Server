package com.g2.gromed.repository;

import com.g2.gromed.entity.Composition;
import com.g2.gromed.entity.CompositionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompositionRepository extends JpaRepository<Composition, CompositionId> {

}
