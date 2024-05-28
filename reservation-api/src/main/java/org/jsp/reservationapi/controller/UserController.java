package org.jsp.reservationapi.controller;

import org.jsp.reservationapi.dto.ResponseStructor;
import org.jsp.reservationapi.dto.UserRequest;
import org.jsp.reservationapi.dto.UserResponse;
import org.jsp.reservationapi.model.User;

import org.jsp.reservationapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/api/users")
public class UserController {
@Autowired
	private UserService userService;
	@PostMapping
	public ResponseEntity<ResponseStructor<UserResponse>> saveUser(@Valid  @RequestBody UserRequest userRequest) {
		return userService.saveUser(userRequest);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<ResponseStructor<UserResponse>> update(@RequestBody UserRequest userRequest, @PathVariable int id) {
		return userService.userUpdate(userRequest, id);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<ResponseStructor<UserResponse>> findById( @PathVariable int id) {
		return userService.findById(id);
	}
	
	
	@DeleteMapping("{id}")
	public ResponseEntity<ResponseStructor<String>> delete(@PathVariable int id) {
		return userService.delete(id);
	}

	
	@PostMapping("/verify-by-password")
	public ResponseEntity<ResponseStructor<UserResponse>> verify(@RequestParam int id, @RequestParam String password) {
		return userService.verify(id, password);
	}
	@PostMapping("/verify-by-email")
	public ResponseEntity<ResponseStructor<UserResponse>> verify(@RequestParam String email, @RequestParam String password) {
		return userService.verify(email, password);
	}
}
