package org.jsp.reservationapi.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

	private int id;
	private String name;
	private String email;
	private long phone;
	private int age;
	private String gender;
	private String password;

}
