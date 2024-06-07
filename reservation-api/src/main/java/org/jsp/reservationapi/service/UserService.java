package org.jsp.reservationapi.service;

import java.util.Optional;

import org.jsp.reservationapi.dao.UserDao;
import org.jsp.reservationapi.dto.AdminResponse;
import org.jsp.reservationapi.dto.EmailConfiguration;
import org.jsp.reservationapi.dto.ResponseStructor;
import org.jsp.reservationapi.dto.UserRequest;
import org.jsp.reservationapi.dto.UserResponse;
import org.jsp.reservationapi.exception.AdminNotFoundException;
import org.jsp.reservationapi.exception.UsernotFoundException;
import org.jsp.reservationapi.model.Admin;
import org.jsp.reservationapi.model.User;
import org.jsp.reservationapi.util.AccountStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;

	
	
	@Autowired
	private ReservationApiMailService mailService;
	@Autowired
	private LinkGeneratorService linkGeneratorService;
	@Autowired
	private ReservationApiMailService reservationApiMailService;
	@Autowired
	private EmailConfiguration emailConfiguration;

	public ResponseEntity<ResponseStructor<UserResponse>> saveUser(UserRequest userRequest, HttpServletRequest requst) {
		ResponseStructor<UserResponse> structor = new ResponseStructor<>();

		User user = MapToUser(userRequest);
		user.setStatus(AccountStatus.IN_ACTIVE.toString());
		user = userDao.saveUser(user);
		String activation_link = linkGeneratorService.getActivationLink(user, requst);

		emailConfiguration.setSubject("Activate ypour aaccount");
		emailConfiguration.setToAddress(user.getEmail());
		emailConfiguration.setText(
				"Dear User Please Activate Your Account by clicking on the following link: " + activation_link);

		structor.setMessage(mailService.sendMail(emailConfiguration));
		structor.setData(mapToUserResponse(user));
		structor.setStatusCode(HttpStatus.CREATED.value());

		return ResponseEntity.status(HttpStatus.CREATED).body(structor);
	}

	public ResponseEntity<ResponseStructor<UserResponse>> userUpdate(UserRequest userRequest, int id) {
		ResponseStructor<UserResponse> structor = new ResponseStructor<>();
		Optional<User> recUser = userDao.findById(id);
		if (recUser.isPresent()) {
			User dbusUser = recUser.get();

			dbusUser.setName(userRequest.getName());
			dbusUser.setEmail(userRequest.getEmail());
			dbusUser.setPhone(userRequest.getPhone());
			dbusUser.setAge(userRequest.getAge());
			dbusUser.setGender(userRequest.getGender());
			dbusUser.setPassword(userRequest.getPassword());
			structor.setData(mapToUserResponse(userDao.saveUser(dbusUser)));
			structor.setMessage("user updated");
			structor.setStatusCode(HttpStatus.ACCEPTED.value());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(structor);

		}

		throw new UsernotFoundException("Cannot Update User as Id is Invalid");
	}

	public ResponseEntity<ResponseStructor<UserResponse>> findById(int id) {
		ResponseStructor<UserResponse> structor = new ResponseStructor<>();
		Optional<User> dbuser = userDao.findById(id);
		if (dbuser.isPresent()) {
			structor.setMessage("user found");
			structor.setData(mapToUserResponse(dbuser.get()));
			structor.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structor);
		}
		throw new UsernotFoundException("Cannot Find User  as Id is Invalid");
	}

	public ResponseEntity<ResponseStructor<String>> delete(int id) {
		ResponseStructor<String> structor = new ResponseStructor<>();
		Optional<User> dbuser = userDao.findById(id);
		if (dbuser.isPresent()) {
			userDao.delete(id);
			structor.setMessage("User Deleted Sussesfull");
			structor.setData("User Found");
			structor.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structor);
		}
		throw new UsernotFoundException("Cannot delete User as Id is Invalid");
	}

	public ResponseEntity<ResponseStructor<UserResponse>> verify(int id, String password) {
		ResponseStructor<UserResponse> structor = new ResponseStructor<>();
		Optional<User> dbuser = userDao.verify(id, password);
		if (dbuser.isPresent()) {
			structor.setMessage("verifyed sussesfull");
			structor.setData(mapToUserResponse(dbuser.get()));
			structor.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structor);
		}

		throw new UsernotFoundException("Cannot Verify User as Id and password is Invalid");
	}

	public ResponseEntity<ResponseStructor<UserResponse>> verify(String email, String password) {
		ResponseStructor<UserResponse> structor = new ResponseStructor<>();
		Optional<User> dbuser = userDao.verify(email, password);
		if (dbuser.isPresent()) {
			structor.setMessage("verifyed sussesfull");
			structor.setData(mapToUserResponse(dbuser.get()));
			structor.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structor);
		}

		throw new UsernotFoundException("Cannot Verify User as Email and password is Invalid");
	}

	public String activate(String token) {

		Optional<User> recUser = userDao.findByToken(token);
		if (recUser.isPresent()) {
			User dbUser = recUser.get();
			dbUser.setStatus("ACTIVE");
			dbUser.setToken(null);
			userDao.saveUser(dbUser);
			return " Your account has been activated";
		}
		throw new AdminNotFoundException("Invalid Token");

	}
	
	
	
	public String forgetPassword(String email, HttpServletRequest request) {
		Optional<User> recUser = userDao.findByEmail(email);
		if (recUser.isPresent()) {
			User user = recUser.get();
			String resetPasswoedLink=linkGeneratorService.getResetPasswordLink(user, request);
			emailConfiguration.setToAddress(email);
			emailConfiguration.setText("please click on the following link to reset your password "+ resetPasswoedLink);
			emailConfiguration.setSubject("RESET YOUR PASSWORD");
			mailService.sendMail(emailConfiguration);
			return "reset password link has been sent to entered mail id";

		}

	throw new AdminNotFoundException(" Invalid mail id");
	}
	
	
	public UserResponse verifyLink( String token) {
		Optional<User> recUser=userDao.findByToken(token);
		if(recUser.isPresent()) {
			User dbUser=recUser.get();
			dbUser.setToken(null);
		     userDao.saveUser(dbUser);
			return mapToUserResponse(dbUser);
		}
		throw  new AdminNotFoundException("link has been expired or it is Invalid");
	}
	
	

	private User MapToUser(UserRequest userRequest) {
		return User.builder().name(userRequest.getName()).email(userRequest.getEmail()).age(userRequest.getAge())
				.gender(userRequest.getGender()).phone(userRequest.getPhone()).password(userRequest.getPassword())
				.build();
	}

	private UserResponse mapToUserResponse(User user) {
		return UserResponse.builder().name(user.getName()).email(user.getEmail()).phone(user.getPhone())
				.age(user.getAge()).gender(user.getGender()).password(user.getPassword()).id(user.getId()).build();
	}

}
