package com.g2.gromed.repository;

import com.g2.gromed.entity.CommandeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandeTypeRepository extends JpaRepository<CommandeType, Long> {

}

