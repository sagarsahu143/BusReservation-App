package org.jsp.reservationapi.dto;

import java.util.List;
import java.util.Optional;

import org.jsp.reservationapi.model.Admin;

import lombok.Data;

@Data
public class ResponseStructor<T> {

	private String message;
	private T data;
	private int statusCode;
}
