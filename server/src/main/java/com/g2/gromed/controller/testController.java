package com.g2.gromed.controller;

import com.g2.gromed.aSUPPRIMER.InsertData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {
	

	
	@Autowired
	private InsertData insertData;
	
	@PostMapping("/poeple")
	public void poeple() throws Exception {
		insertData.transformData();
	}
}
