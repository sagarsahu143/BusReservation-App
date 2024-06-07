package org.jsp.reservationapi.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.jsp.reservationapi.dao.AdminDao;
import org.jsp.reservationapi.dao.BusDao;
import org.jsp.reservationapi.dto.AdminRequest;
import org.jsp.reservationapi.dto.AdminResponse;
import org.jsp.reservationapi.dto.EmailConfiguration;
import org.jsp.reservationapi.dto.ResponseStructor;
import org.jsp.reservationapi.exception.AdminNotFoundException;
import org.jsp.reservationapi.model.Admin;
import org.jsp.reservationapi.util.AccountStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import net.bytebuddy.utility.RandomString;

@Service
public class AdminService {
	@Autowired
	private AdminDao adminDao;
	@Autowired
	private ReservationApiMailService mailService;

	@Autowired
	private LinkGeneratorService linkGeneratorService;
	@Autowired
	private ReservationApiMailService reservationApiMailService;
	@Autowired
	private EmailConfiguration emailConfiguration;

	public ResponseEntity<ResponseStructor<AdminResponse>> saveAdmin(AdminRequest adminRequest,
			HttpServletRequest request) {
		ResponseStructor<AdminResponse> structor = new ResponseStructor<>();

//		String siteUrl = request.getRequestURL().toString();
//		String path = request.getServletPath();
//		String activation_link = siteUrl.replace(path, "/api/admins/activate");
//		String token = RandomString.make(23);
//		activation_link += "?token " + token;
//		System.out.println(activation_link);

		Admin admin = mapToAdmin(adminRequest);
		admin.setStatus(AccountStatus.IN_ACTIVE.toString());
		admin = adminDao.saveAdmin(admin);

		String activation_link = linkGeneratorService.getActivationLink(admin, request);
		emailConfiguration.setSubject("Activate ypour aaccount");
		emailConfiguration.setToAddress(admin.getEmail());
		emailConfiguration.setText("Dear Admin Please Activate Your Account by clicking on the following link: " + activation_link);
		
		structor.setMessage(mailService.sendMail(emailConfiguration));
		structor.setData(mapToAdminResponse(admin));
		structor.setStatusCode(HttpStatus.CREATED.value());
		return ResponseEntity.status(HttpStatus.CREATED).body(structor);

	}

	public ResponseEntity<ResponseStructor<AdminResponse>> update(AdminRequest adminRequest, int id) {
		ResponseStructor<AdminResponse> structor = new ResponseStructor<>();
		Optional<Admin> recAdmin = adminDao.findById(id);
		if (recAdmin.isPresent()) {
			Admin dbadmin = recAdmin.get();
			dbadmin.setName(adminRequest.getName());
			dbadmin.setEmail(adminRequest.getEmail());
			dbadmin.setGst_number(adminRequest.getGst_number());
			dbadmin.setPhone(adminRequest.getPhone());
			dbadmin.setTravels_name(adminRequest.getTravels_name());
			dbadmin.setPassword(adminRequest.getPassword());

			structor.setData(mapToAdminResponse(adminDao.saveAdmin(dbadmin)));
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
			structor.setData(mapToAdminResponse(admins.get()));
			structor.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structor);
		}

		throw new AdminNotFoundException(" Cannot Find Admin as Id is Invalid ");
	}

	public ResponseEntity<ResponseStructor<AdminResponse>> verify(String email, String password) {
		ResponseStructor<AdminResponse> structor = new ResponseStructor<>();

		Optional<Admin> admins = adminDao.verify(email, password);
		if (admins.isPresent()) {

			Admin dbAdmin = admins.get();
			if (dbAdmin.getStatus().equals(AccountStatus.IN_ACTIVE.toString()))
				throw new IllegalStateException("Please Active your acount before sign up");
			structor.setMessage("Admin found");
			structor.setData(mapToAdminResponse(admins.get()));
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

	public String activate(String token) {

		Optional<Admin> recAdmin = adminDao.findByToken(token);
		if (recAdmin.isPresent()) {
			Admin dbAdmin = recAdmin.get();
			dbAdmin.setStatus("ACTIVE");
			dbAdmin.setToken(null);
			adminDao.saveAdmin(dbAdmin);
			return " Your account has been activated";
		}
		throw new AdminNotFoundException("Invalid Token");

	}

	public String forgetPassword(String email, HttpServletRequest request) {
		Optional<Admin> recAdmin = adminDao.findByEmail(email);
		if (recAdmin.isPresent()) {
			Admin admin = recAdmin.get();
			String resetPasswoedLink=linkGeneratorService.getResetPasswordLink(admin, request);
			emailConfiguration.setToAddress(email);
			emailConfiguration.setText("please click on the following link to reset your password "+ resetPasswoedLink);
			emailConfiguration.setSubject("RESET YOUR PASSWORD");
			mailService.sendMail(emailConfiguration);
			return "reset password link has been sent to entered mail id";

		}

	throw new AdminNotFoundException(" Invalid mail id");
	}
	
	
	
	public AdminResponse verifyLink( String token) {
		Optional<Admin> recAdmin=adminDao.findByToken(token);
		if(recAdmin.isPresent()) {
			Admin dbAdmin=recAdmin.get();
			dbAdmin.setToken(null);
			adminDao.saveAdmin(dbAdmin);
			return mapToAdminResponse(dbAdmin);
		}
		throw  new AdminNotFoundException("link has been expired or it is Invalid");
	}

	private Admin mapToAdmin(AdminRequest adminRequest) {
		return Admin.builder().name(adminRequest.getName()).email(adminRequest.getEmail())
				.gst_number(adminRequest.getGst_number()).phone(adminRequest.getPhone())
				.travels_name(adminRequest.getTravels_name()).password(adminRequest.getPassword()).build();
	}

	private AdminResponse mapToAdminResponse(Admin admin) {
		return AdminResponse.builder().name(admin.getName()).email(admin.getEmail()).gst_number(admin.getGst_number())
				.phone(admin.getPhone()).travels_name(admin.getTravels_name()).password(admin.getPassword())
				.id(admin.getId()).build();
	}

}
