package com.g2.gromed.repository;

import com.g2.gromed.entity.Medicament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicamentRepository extends JpaRepository<Medicament, String> {

}
