package com.g2.gromed.repository;

import com.g2.gromed.entity.GroupeGenerique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupeGeneriqueRepository extends JpaRepository<GroupeGenerique, Long> {

}
