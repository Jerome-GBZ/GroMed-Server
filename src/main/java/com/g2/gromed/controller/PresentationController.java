package com.g2.gromed.controller;

import com.g2.gromed.entity.Presentation;
import com.g2.gromed.service.PresentationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/presentation")
public class PresentationController {
	@Autowired
	private PresentationService presentationService;
	
	@ApiOperation(value = "Get all presentations")
	@GetMapping("/all")
	public Presentation getAllPresentations() {
		return presentationService.getAllPresentations(PageRequest.of(0,10));
	}
	@ApiOperation(value = "Get all presentations")
	@GetMapping("/all2")
	public Presentation getAllPresentations2() {
		return presentationService.getAllPresentations2(PageRequest.of(0,10));
	}
}
