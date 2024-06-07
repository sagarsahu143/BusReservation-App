package org.jsp.reservationapi.exception;

public class BusNotFoundException  extends RuntimeException{

	public BusNotFoundException(String message) {
		super(message);
	}

}
