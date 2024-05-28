package org.jsp.reservationapi.controller;

import java.util.List;

import org.jsp.reservationapi.dto.AdminRequest;
import org.jsp.reservationapi.dto.AdminResponse;
import org.jsp.reservationapi.dto.ResponseStructor;
import org.jsp.reservationapi.model.Admin;
import org.jsp.reservationapi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

	@Autowired

	private AdminService adminService;

	@PostMapping
	public ResponseEntity<ResponseStructor<AdminResponse>> saveAdmin(@Valid @RequestBody AdminRequest adminRequest) {
		return adminService.saveAdmin(adminRequest);
	}

	@PutMapping("{id}")
	public ResponseEntity<ResponseStructor<AdminResponse>> update(@RequestBody AdminRequest adminRequest ,@PathVariable int id) {
		return adminService.update(adminRequest,id);
	}

	@GetMapping("{id}")
	public ResponseEntity<ResponseStructor<AdminResponse>> findById(@PathVariable int id) {
		return adminService.findById(id);

	}

	@GetMapping
	public ResponseEntity<ResponseStructor<List<Admin>>> findAllAdmin() {
		return adminService.findAll();
	}

	@DeleteMapping("{id}")
	public ResponseEntity<ResponseStructor<String>> delete(@PathVariable int id) {

		return adminService.delete(id);

	}
}
