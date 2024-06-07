package org.jsp.reservationapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminRequest {
	@NotBlank(message = "Name is mandatory")
	private String name;
	private long phone;
	@Email(message = "Invalid Format")
	@NotBlank(message = "Email is mendatory")
	private String email;
	@NotBlank (message = "Gst is mendatory")
	@Size(min =10,max=15,message = "Gst number mus have 15 character")
	private String gst_number;
	@NotBlank(message = "Password is mendatory")
	@Size(min = 8 ,max =10,message = "password mus have 8 to 1o digit")
	private String password;
	@NotBlank(message = "name is mendatory")
	private String travels_name;

}
