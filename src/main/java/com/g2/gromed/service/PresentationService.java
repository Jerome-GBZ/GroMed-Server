package com.g2.gromed.service;

import com.g2.gromed.entity.Presentation;
import com.g2.gromed.repository.PresentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PresentationService {
	@Autowired
	private PresentationRepository presentationRepository;
	
	public Page<Presentation> getAllPresentations(Pageable pagination) {
		return presentationRepository.findAll(pagination);
	}
	public Page<Presentation> getAllPresentations2(Pageable pagination) {
		return presentationRepository.findAllPresentation(pagination);
	}
}
