package org.jsp.reservationapi.service;

import java.util.List;
import java.util.Optional;

import org.jsp.reservationapi.dao.AdminDao;
import org.jsp.reservationapi.dao.BusDao;
import org.jsp.reservationapi.dto.AdminRequest;
import org.jsp.reservationapi.dto.AdminResponse;
import org.jsp.reservationapi.dto.ResponseStructor;
import org.jsp.reservationapi.exception.AdminNotFoundException;
import org.jsp.reservationapi.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
	@Autowired
	private AdminDao adminDao;

	public ResponseEntity<ResponseStructor<AdminResponse>> saveAdmin(AdminRequest adminRequest) {

		ResponseStructor<AdminResponse> structor = new ResponseStructor<>();
		structor.setMessage("admin created");

//		structor.setData(adminDao.saveAdmin(mapToAdmin(adminRequest)));    //without dto responce

		Admin admin = adminDao.saveAdmin(mapToAdmin(adminRequest));
		structor.setData(mapToResponse(admin));                   
		structor.setStatusCode(HttpStatus.CREATED.value());
		return ResponseEntity.status(HttpStatus.CREATED).body(structor);
	}

	public ResponseEntity<ResponseStructor<AdminResponse>> update(AdminRequest adminRequest, int id) {
		ResponseStructor<AdminResponse> structor = new ResponseStructor<>();
		Optional<Admin> recAdmin = adminDao.findById(id);
		if (recAdmin.isPresent()) {
			Admin dbadmin =recAdmin.get();
			dbadmin.setName(adminRequest.getName());
			dbadmin.setEmail(adminRequest.getEmail());
			dbadmin.setGst_number(adminRequest.getGst_number());
			dbadmin.setPhone(adminRequest.getPhone());
			dbadmin.setTravels_name(adminRequest.getTravels_name());
			dbadmin.setPassword(adminRequest.getPassword());
			
			structor.setData(mapToResponse(adminDao.saveAdmin(dbadmin)));
			structor.setMessage("admin updated");
			structor.setStatusCode(HttpStatus.ACCEPTED.value());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(structor);

		}
		throw new AdminNotFoundException("Cannot Update Admin as Id is Invalid");
	}

	public ResponseEntity<ResponseStructor<AdminResponse>> findById(int id) {
		ResponseStructor<AdminResponse> structor = new ResponseStructor<>();

		Optional<Admin> admins = adminDao.findById(id);
		if (admins.isPresent()) {
			structor.setMessage("Admin found");
			structor.setData(mapToResponse(admins.get()));
			structor.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structor);
		}

		throw new AdminNotFoundException(" Cannot Find Admin as Id is Invalid ");
	}
	
	
	public ResponseEntity<ResponseStructor<AdminResponse>> verify(String email,String password) {
		ResponseStructor<AdminResponse> structor = new ResponseStructor<>();

		Optional<Admin> admins = adminDao.verify(email, password);
		if (admins.isPresent()) {
			structor.setMessage("Admin found");
			structor.setData(mapToResponse(admins.get()));
			structor.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structor);
		}

		throw new AdminNotFoundException(" Cannot Find Admin as email and password is Invalid ");
	}


	public ResponseEntity<ResponseStructor<List<Admin>>> findAll() {
		ResponseStructor<List<Admin>> structor = new ResponseStructor<>();
		
		structor.setData(adminDao.findAll());
		structor.setMessage("find all admins");
		structor.setStatusCode(HttpStatus.OK.value());

		return ResponseEntity.status(HttpStatus.OK).body(structor);
	}

	public ResponseEntity<ResponseStructor<String>> delete(int id) {

		ResponseStructor<String> structor = new ResponseStructor<>();
		Optional<Admin> dbadmin = adminDao.findById(id);
		if (dbadmin.isPresent()) {
			adminDao.delete(id);
			structor.setMessage("admin deleted");
			structor.setData("admin found");
			structor.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structor);

		}

		throw new AdminNotFoundException("Cannot DElete Admin as Id is Invalid");

	}

	private Admin mapToAdmin(AdminRequest adminRequest) {
		return Admin.builder().name(adminRequest.getName()).email(adminRequest.getEmail())
				.gst_number(adminRequest.getGst_number()).phone(adminRequest.getPhone())
				.travels_name(adminRequest.getTravels_name()).password(adminRequest.getPassword()).build();
	}

	private AdminResponse mapToResponse(Admin admin) {
		return AdminResponse.builder().name(admin.getName()).email(admin.getEmail()).gst_number(admin.getGst_number())
				.phone(admin.getPhone()).travels_name(admin.getTravels_name()).password(admin.getPassword()).id(admin.getId()).build();
	}
	

}
