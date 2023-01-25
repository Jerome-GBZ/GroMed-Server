package com.g2.gromed.composant;

import com.g2.gromed.repository.ICompositionRepository;
import com.g2.gromed.repository.IMedicamentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.util.List;

@Log
@Component
@AllArgsConstructor
public class FiltreComposant {

    private IMedicamentRepository medicamentRepository;
    private ICompositionRepository compositionRepository;

    public List<String> getAllSubstancesDenomination() {
        return compositionRepository.findAllDenominationsSubstances();
    }

    public List<String> getAllTitulaires(){
        return medicamentRepository.findAllTitulaires();
    }
}
