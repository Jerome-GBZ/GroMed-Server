package com.g2.gromed.composant;

import com.g2.gromed.entity.Presentation;
import com.g2.gromed.model.dto.filtre.FiltreDTO;
import com.g2.gromed.queryBuilder.PresentationFiltreQueryBuilder;
import com.g2.gromed.repository.IPresentationRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class PresentationComposant {

	@PersistenceContext
	private EntityManager entityManager;

	private IPresentationRepository presentationRepository;
	
	public Page<Presentation> getPresentations(Pageable pagination, FiltreDTO filtreDTO){

		String queryString = PresentationFiltreQueryBuilder.createQuery(filtreDTO, pagination);
		Query query = entityManager.createQuery(queryString);

		if(!filtreDTO.getPresentationName().equals("")){
			query.setParameter("denomination", "%"+ filtreDTO.getPresentationName() + "%");
		}
		if(!filtreDTO.getTitulaires().isEmpty()){
			query.setParameter("titulaires", filtreDTO.getTitulaires());
		}
		query.setFirstResult(pagination.getPageNumber() * pagination.getPageSize());
		query.setMaxResults(pagination.getPageSize());

		PresentationFiltreQueryBuilder.createQuery(filtreDTO, pagination);
		List result = query.getResultList();

		return new PageImpl<>(result, pagination, result.size());
	}
	
	public Presentation getPresentationByCodeCIP7(String codeCIP7){
		return presentationRepository.findFirstByCodeCIP7(codeCIP7);
	}
	
	public void updatePresentation(Presentation presentation){
		presentationRepository.save(presentation);
	}
}
