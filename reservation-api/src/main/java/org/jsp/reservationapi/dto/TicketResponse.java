package org.jsp.reservationapi.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketResponse {
	
	private int id;
	private String busName;
	private LocalDateTime dateAndTimeOfBooking;
	private double cost;
	private String status;
	private int numberOfSeatesBooked;
	private String BusNumber;
	private LocalDate dateOfDeparture;
	private String userName ;
	private long phone;
	private int age;
	private String gender;
	private  String formLocation;
	private  String toLocation;

}
