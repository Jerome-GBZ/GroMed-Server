package com.g2.gromed.repository;

import com.g2.gromed.entity.Composition;
import com.g2.gromed.entity.GroupeGenerique;
import com.g2.gromed.entity.Medicament;
import com.g2.gromed.entity.Presentation;
import com.g2.gromed.model.dto.filtre.FiltreDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public interface IPresentationRepository extends JpaRepository<Presentation, Long> {
	Presentation findFirstByCodeCIP7(String codeCIP7);

	default Page<Presentation> getAllFromCriterias(EntityManager entityManager, FiltreDTO filtreDTO, Pageable pageable){
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Presentation> cq = cb.createQuery(Presentation.class);
		Root<Presentation> root = cq.from(Presentation.class);
		Join<Presentation, Medicament> medicamentJoin = root.join("medicament");
		Join<Medicament, GroupeGenerique> groupeGeneriqueJoin = medicamentJoin.join("groupeGeneriques", JoinType.LEFT);
		Join<Medicament, Composition> compositionJoin = medicamentJoin.join("compositions", JoinType.LEFT);

		List<Predicate> predicateList = new ArrayList<>();

		if(!filtreDTO.getPresentationName().equals("")){
			predicateList.add(cb.like(medicamentJoin.get("denomination"), "%" + filtreDTO.getPresentationName() + "%"));
		}

		if(filtreDTO.isAvailable()){
			predicateList.add(cb.greaterThan(root.get("stock"), 0));
		}

		if(filtreDTO.getTitulaires() != null && !filtreDTO.getTitulaires().isEmpty()){
			predicateList.add(medicamentJoin.get("titulaire").in(filtreDTO.getTitulaires()));
		}

		if(filtreDTO.isOriginal() && !filtreDTO.isGenerique()){
			predicateList.add(cb.isNull(groupeGeneriqueJoin.get("medicament")));
		}

		if(filtreDTO.isGenerique() && !filtreDTO.isOriginal()){
			predicateList.add(cb.isNotNull(groupeGeneriqueJoin.get("medicament")));
		}

		if(filtreDTO.getSubstancesDenomitations() != null && !filtreDTO.getSubstancesDenomitations().isEmpty()){
			predicateList.add(compositionJoin.get("denominationSubstance").in(filtreDTO.getSubstancesDenomitations()));
		}

		cq.where(predicateList.toArray(new Predicate[]{}));
		List<Presentation> presentationList = entityManager
				.createQuery(cq)
				.setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
				.setMaxResults(pageable.getPageSize())
				.getResultList();

		return new PageImpl<>(presentationList, pageable, presentationList.size());
	}

}
