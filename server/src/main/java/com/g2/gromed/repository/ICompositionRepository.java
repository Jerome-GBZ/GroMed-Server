package com.g2.gromed.repository;

import com.g2.gromed.entity.Composition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  ICompositionRepository extends JpaRepository<Composition, Long> {
        @Query("select distinct denominationSubstance from Composition")
        List<String> findAllDenominationsSubstances();
}
