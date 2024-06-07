package org.jsp.reservationapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
      @NotBlank(message = "name is mendatory")
	private String name;
      @Email(message = "Invalid Format")
      @NotBlank(message = "Email is mendatory")
	private String email;
	private long phone;
	private int age;
	@NotBlank
	private String gender;
	@NotBlank(message = "Password is mendatory")
	@Size( min =8 ,max = 12,message = "password must Have 8 to 12 digit")
	private String password;
	
	
}
