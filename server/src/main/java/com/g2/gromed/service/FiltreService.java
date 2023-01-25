package com.g2.gromed.service;

import com.g2.gromed.composant.FiltreComposant;
import com.g2.gromed.mapper.IFiltreMapper;
import com.g2.gromed.model.dto.filtre.FiltreDTO;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Log
@Service
@AllArgsConstructor
public class FiltreService {

    private FiltreComposant filtreComposant;
    private IFiltreMapper filtreMapper;

    public FiltreDTO getAllFilters(){
        return filtreMapper.toFiltreDTO(filtreComposant.getAllTitulaires(), filtreComposant.getAllSubstancesDenomination());
    }
}
