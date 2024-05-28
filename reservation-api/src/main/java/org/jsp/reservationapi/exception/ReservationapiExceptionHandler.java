package org.jsp.reservationapi.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.message.Message;
import org.jsp.reservationapi.dto.ResponseStructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ReservationapiExceptionHandler {

	@ExceptionHandler(AdminNotFoundException.class)
	public ResponseEntity<ResponseStructor<String>> handle(AdminNotFoundException exception) {
		
		ResponseStructor<String> structor=new ResponseStructor<>();
		structor.setData(exception.getMessage());
		structor.setMessage("Admin Not Found");
		structor.setStatusCode(HttpStatus.NOT_FOUND.value());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(structor);
	}
	
	@ExceptionHandler(UsernotFoundException.class)
	public ResponseEntity<ResponseStructor<String>> handle(UsernotFoundException exception) {
		
		ResponseStructor<String> structor=new ResponseStructor<>();
		structor.setData(exception.getMessage());
		structor.setMessage("User Not Found");
		structor.setStatusCode(HttpStatus.NOT_FOUND.value());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(structor);
	}
	
	
	
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public   Map<String, String> handleValidationException(MethodArgumentNotValidException ex){
		
		Map<String, String> errors= new HashMap<>();
	List<ObjectError> objectErrors =	ex.getBindingResult().getAllErrors();
	for(ObjectError objectError:objectErrors) {
		String fieldName= ((FieldError)objectError).getField();
		String errorMessage=objectError.getDefaultMessage();
		errors.put(fieldName, errorMessage);
		
	}
		
		return errors;
	}
	
	
	
}
