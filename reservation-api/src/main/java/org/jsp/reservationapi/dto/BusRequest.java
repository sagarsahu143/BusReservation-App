package org.jsp.reservationapi.dto;

import java.time.LocalDate;

import org.jsp.reservationapi.model.Admin;
import org.jsp.reservationapi.model.Bus;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusRequest{

	
	private int id;
	private String name;
	private LocalDate depature_date;
	private String bus_no;
	private String form_loc;
	private String to_loc;
	private int total_seat;
	private double cost;
//	private int avalable_seat;

}
