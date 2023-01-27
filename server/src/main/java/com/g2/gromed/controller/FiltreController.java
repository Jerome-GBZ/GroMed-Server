package com.g2.gromed.controller;

import com.g2.gromed.endpoint.IFiltreEndpoint;
import com.g2.gromed.model.dto.filtre.FiltreDTO;
import com.g2.gromed.service.FiltreService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@AllArgsConstructor
public class FiltreController implements IFiltreEndpoint {

    private FiltreService filtreService;

    @Override
    public ResponseEntity<FiltreDTO> getFiltres(){
        FiltreDTO filtre = filtreService.getAllFilters();
        return filtre != null ? ResponseEntity.ok(filtre) : ResponseEntity.notFound().build();
    }
}
