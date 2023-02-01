package com.g2.gromed.controller;

import com.g2.gromed.endpoint.IAdminEndpoint;
import com.g2.gromed.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin // NOSONAR
@RestController
@AllArgsConstructor
public class AdminController implements IAdminEndpoint{
	private AdminService adminService;
	@Override
	public ResponseEntity<Boolean> refill() {
		return ResponseEntity.ok(adminService.refill());
	}
}
