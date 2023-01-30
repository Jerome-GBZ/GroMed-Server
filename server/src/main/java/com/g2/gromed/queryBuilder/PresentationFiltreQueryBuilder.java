package com.g2.gromed.queryBuilder;

import com.g2.gromed.model.dto.filtre.FiltreDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Pageable;

public abstract class PresentationFiltreQueryBuilder {

    @PersistenceContext
    private static EntityManager entityManager;

    private PresentationFiltreQueryBuilder(){}

    public static String createQuery(FiltreDTO filtreDTO, Pageable pagination){
        boolean firstWhere = true;
        String queryString = "select p from Presentation p";
        queryString = getJoinTables(filtreDTO, queryString);
        if(!filtreDTO.getPresentationName().equals("")){
            queryString += " where lower(m.denomination) like lower(:denomination)";
            firstWhere = false;
        }
        if(filtreDTO.isAvailable()){
            queryString += firstWhere ? " where" : " and";
            queryString += " p.stock > 0";
        }
        if(!filtreDTO.getTitulaires().isEmpty()){
            queryString += firstWhere ? " where" : " and";
            queryString += " m.titulaire in :titulaires";
        }

        return queryString;
    }

    private static String getJoinTables(FiltreDTO filtreDTO, String query){
        if(!filtreDTO.getPresentationName().equals("") || !filtreDTO.getTitulaires().isEmpty() || !filtreDTO.getSubstancesDenomitations().isEmpty()){
            query += " join p.medicament m";
        }
        return query;
    }
}
