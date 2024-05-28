package org.jsp.reservationapi.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false, unique = true)

	private String email;
	@Column(nullable = false, unique = true)

	private String gst_number;
	@Column(nullable = false, unique = true)

	private long phone;
	@Column(nullable = false)

	private String password;
	@Column(nullable = false)

	private String travels_name;

	@OneToMany(mappedBy = "admins")
	private List<Bus> buss;

}
