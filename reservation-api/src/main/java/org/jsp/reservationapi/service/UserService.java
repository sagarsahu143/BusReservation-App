package org.jsp.reservationapi.service;

import java.util.Optional;

import org.jsp.reservationapi.dao.UserDao;
import org.jsp.reservationapi.dto.ResponseStructor;
import org.jsp.reservationapi.dto.UserRequest;
import org.jsp.reservationapi.dto.UserResponse;
import org.jsp.reservationapi.exception.AdminNotFoundException;
import org.jsp.reservationapi.exception.UsernotFoundException;
import org.jsp.reservationapi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;

	public ResponseEntity<ResponseStructor<UserResponse>> saveUser(UserRequest userRequest) {
		ResponseStructor<UserResponse> structor = new ResponseStructor<>();
		structor.setMessage("user saved");
		structor.setData(mapToUserResponse(userDao.saveUser(MapToUser(userRequest))));
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

		throw new  UsernotFoundException("Cannot Update User as Id is Invalid");
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
		throw new  UsernotFoundException("Cannot Find User  as Id is Invalid");	}

	public ResponseEntity<ResponseStructor<String>> delete(int id) {
		ResponseStructor<String> structor = new ResponseStructor<>();
		Optional<User> dbuser = userDao.findById(id);
		if (dbuser.isPresent()) {
			userDao.delete(id);
			structor.setMessage("Uuser Deleted");
			structor.setData("user found");
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

		throw new  UsernotFoundException("Cannot Verify User as Email and password is Invalid");	}

	private User MapToUser(UserRequest userRequest) {
		return User.builder().name(userRequest.getName()).email(userRequest.getEmail()).age(userRequest.getAge())
				.gender(userRequest.getGender()).phone(userRequest.getPhone()).password(userRequest.getPassword())
				.build();
	}

	private UserResponse mapToUserResponse(User user) {
		return UserResponse.builder().name(user.getName()).email(user.getEmail()).phone(user.getPhone())
				.age(user.getAge()).gender(user.getGender()).password(user.getPassword()).build();
	}

}
