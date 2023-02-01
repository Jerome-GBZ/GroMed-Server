package com.g2.gromed.repository;

import com.g2.gromed.entity.Composition;
import com.g2.gromed.entity.GroupeGenerique;
import com.g2.gromed.entity.Medicament;
import com.g2.gromed.entity.Presentation;
import com.g2.gromed.model.dto.filtre.FiltreDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public interface IPresentationRepository extends JpaRepository<Presentation, Long> {
    Presentation findFirstByCodeCIP7(String codeCIP7);

    default Page<Presentation> getAllFromCriterias(EntityManager entityManager, FiltreDTO filtreDTO, Pageable pageable) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Presentation> cq = cb.createQuery(Presentation.class);
        Root<Presentation> root = cq.from(Presentation.class);
        Join<Presentation, Medicament> medicamentJoin = root.join("medicament");
        Join<Medicament, GroupeGenerique> groupeGeneriqueJoin = medicamentJoin.join("groupeGeneriques", JoinType.LEFT);
        Join<Medicament, Composition> compositionJoin = medicamentJoin.join("compositions", JoinType.LEFT);

        List<Predicate> predicateList = new ArrayList<>();

        if (!filtreDTO.getPresentationName().equals("")) {
            predicateList.add(cb.like(cb.lower(medicamentJoin.get("denomination")), "%" + filtreDTO.getPresentationName().toLowerCase() + "%"));
        }

        if (filtreDTO.isAvailable()) {
            predicateList.add(cb.greaterThan(root.get("stock"), 0));
        }

        if (filtreDTO.getTitulaires() != null && !filtreDTO.getTitulaires().isEmpty()) {
            predicateList.add(medicamentJoin.get("titulaire").in(filtreDTO.getTitulaires()));
        }

        if (filtreDTO.isOriginal() && !filtreDTO.isGenerique()) {
            predicateList.add(cb.isNull(groupeGeneriqueJoin.get("medicament")));
        }

        if (filtreDTO.isGenerique() && !filtreDTO.isOriginal()) {
            predicateList.add(cb.isNotNull(groupeGeneriqueJoin.get("medicament")));
        }

        if (filtreDTO.getSubstancesDenomitations() != null && !filtreDTO.getSubstancesDenomitations().isEmpty()) {
            predicateList.add(compositionJoin.get("denominationSubstance").in(filtreDTO.getSubstancesDenomitations()));
        }

        Sort sorts = pageable.getSort();
        sortFunction(sorts, cb, cq, root, medicamentJoin);

        if(!predicateList.isEmpty()) {
            cq.where(predicateList.toArray(new Predicate[]{}));
        }
        System.out.println("first = " + pageable.getPageNumber() * pageable.getPageSize());
        System.out.println("max = " + pageable.getPageSize());
        List<Presentation> presentationList = entityManager
                .createQuery(cq)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

/*
		CriteriaBuilder cbCount = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> cqCount = cbCount.createQuery(Long.class);
		Root<Presentation> rootCount = cqCount.from(Presentation.class);
		Join<Presentation, Medicament> medicamentJoinCount = rootCount.join("medicament");
		Join<Medicament, GroupeGenerique> groupeGeneriqueJoinCount = medicamentJoinCount.join("groupeGeneriques", JoinType.LEFT);
		Join<Medicament, Composition> compositionJoinCount = medicamentJoinCount.join("compositions", JoinType.LEFT);
		cqCount.select(cbCount.countDistinct(rootCount.get("codeCIP7")));
		List<Predicate> predicateListCount = new ArrayList<>();

		if (!filtreDTO.getPresentationName().equals("")) {
			predicateListCount.add(cbCount.like(cbCount.lower(medicamentJoinCount.get("denomination")), "%" + filtreDTO.getPresentationName().toLowerCase() + "%"));
		}

		if (filtreDTO.isAvailable()) {
			predicateListCount.add(cbCount.greaterThan(rootCount.get("stock"), 0));
		}

		if (filtreDTO.getTitulaires() != null && !filtreDTO.getTitulaires().isEmpty()) {
			predicateListCount.add(medicamentJoinCount.get("titulaire").in(filtreDTO.getTitulaires()));
		}

		if (filtreDTO.isOriginal() && !filtreDTO.isGenerique()) {
			predicateListCount.add(cbCount.isNull(groupeGeneriqueJoinCount.get("medicament")));
		}

		if (filtreDTO.isGenerique() && !filtreDTO.isOriginal()) {
			predicateListCount.add(cbCount.isNotNull(groupeGeneriqueJoinCount.get("medicament")));
		}

		if (filtreDTO.getSubstancesDenomitations() != null && !filtreDTO.getSubstancesDenomitations().isEmpty()) {
			predicateListCount.add(compositionJoinCount.get("denominationSubstance").in(filtreDTO.getSubstancesDenomitations()));
		}

        if(!predicateListCount.isEmpty()) {
            cqCount.where(predicateListCount.toArray(new Predicate[]{}));
        }
		Long count = entityManager
				.createQuery(cqCount)
				.getSingleResult();
//
//        int firstIndex = pageable.getPageNumber() * pageable.getPageSize();
//        int lastIndex = firstIndex + pageable.getPageSize();
//        int resultLast = presentationList.size();
//
//        if (firstIndex <= resultLast && lastIndex > resultLast) {
//            lastIndex = resultLast;
//        } else if (firstIndex > resultLast && lastIndex > resultLast) {
//            firstIndex = 0;
//            lastIndex = Math.min(presentationList.size(), pageable.getPageSize());
//            pageable = PageRequest.of(0, pageable.getPageSize());
//        }

//        List<Presentation> result = presentationList.subList(firstIndex, lastIndex);*/

        return new PageImpl<>(presentationList, pageable, 10);
    }

    private static void sortFunction(Sort sorts, CriteriaBuilder cb, CriteriaQuery<Presentation> cq, Root<Presentation> root, Join<Presentation, Medicament> medicamentJoin) {
        String deno = "denomination";
        String prix = "prix";
        setSort(sorts, cb, cq, deno, medicamentJoin.get(deno));
        setSort(sorts, cb, cq, prix, root.get(prix));
    }

    static void setSort(Sort sorts, CriteriaBuilder cb, CriteriaQuery<Presentation> cq, String deno, Path<Object> objectPath) {
        Sort.Order denominationOrder = sorts.getOrderFor(deno);
        if (denominationOrder != null && denominationOrder.isAscending()) {
            cq.orderBy(cb.asc(objectPath));
        } else if (denominationOrder != null && denominationOrder.isDescending()) {
            cq.orderBy(cb.desc(objectPath));
        }
    }

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "10000")})
    List<Presentation> findByCodeCIP7In(List<String> codeCIP7);


    List<Presentation> findByStockLessThanEqual(int i);
}
