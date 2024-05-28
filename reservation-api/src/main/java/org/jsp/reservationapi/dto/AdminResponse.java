package org.jsp.reservationapi.dto;

import java.util.List;

import org.jsp.reservationapi.model.Bus;

import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminResponse {

	private int id;
	private String name;

	private String email;

	private String gst_number;

	private long phone;

	private String password;

	private String travels_name;

}
