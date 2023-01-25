package com.g2.gromed.repository;

import com.g2.gromed.entity.Medicament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMedicamentRepository extends JpaRepository<Medicament, Long> {
    @Query("select distinct titulaire from Medicament")
    List<String> findAllTitulaires();
}
