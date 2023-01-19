package com.g2.gromed.repository;

import com.g2.gromed.entity.Etablissement;
import com.g2.gromed.entity.EtablissementId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtablissementRepository extends JpaRepository<Etablissement, EtablissementId> {

}
