package org.jsp.reservationapi.controller;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.jsp.reservationapi.dto.AdminRequest;
import org.jsp.reservationapi.dto.AdminResponse;
import org.jsp.reservationapi.dto.ResponseStructor;
import org.jsp.reservationapi.model.Admin;
import org.jsp.reservationapi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import net.bytebuddy.asm.Advice.Return;

@CrossOrigin
@RestController
@RequestMapping("/api/admins")
public class AdminController {

	@Autowired

	private AdminService adminService;

	@PostMapping
	public ResponseEntity<ResponseStructor<AdminResponse>> saveAdmin(@Valid @RequestBody AdminRequest adminRequest,
			HttpServletRequest request) {
		return adminService.saveAdmin(adminRequest, request);
	}

	@PutMapping("{id}")
	public ResponseEntity<ResponseStructor<AdminResponse>> update(@RequestBody AdminRequest adminRequest,
			@PathVariable int id) {
		return adminService.update(adminRequest, id);
	}

	@GetMapping("{id}")
	public ResponseEntity<ResponseStructor<AdminResponse>> findById(@PathVariable int id) {
		return adminService.findById(id);

	}

	@PostMapping("/verify-by-email")
	public ResponseEntity<ResponseStructor<AdminResponse>> verify(@RequestParam String email,
			@RequestParam String password) {
		return adminService.verify(email, password);
	}

	@GetMapping
	public ResponseEntity<ResponseStructor<List<Admin>>> findAllAdmin() {
		return adminService.findAll();
	}

	@DeleteMapping("{id}")
	public ResponseEntity<ResponseStructor<String>> delete(@PathVariable int id) {

		return adminService.delete(id);

	}

	@GetMapping("/activate")
	public String activate(@RequestParam String token) {
		return adminService.activate(token);
	}

	@PostMapping("/forget-password")
	public String forgetPassword(@RequestParam String email, HttpServletRequest request) {
		return adminService.forgetPassword(email, request);

	}

	@GetMapping("/verify-link")
	public AdminResponse verifyResetPasswordLink(@RequestParam String token, HttpServletResponse response) {

		AdminResponse adminResponse = adminService.verifyLink(token);
		if (adminResponse != null) {
			try {
				response.sendRedirect("http://localhost:3000/admin-reset-password");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return adminResponse;

	}

}
