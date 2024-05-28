package org.jsp.reservationapi.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bus {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private LocalDate depature_date;
	@Column(nullable = false)
	private String bus_no;
	@Column(nullable = false)
	private String form_loc;
	@Column(nullable = false)
	private String to_loc;
	@Column(nullable = false)
	private int total_seat;
	@ManyToOne
	@JoinColumn(name="admin_id")
	@JsonIgnore
	private Admin admins;

}
