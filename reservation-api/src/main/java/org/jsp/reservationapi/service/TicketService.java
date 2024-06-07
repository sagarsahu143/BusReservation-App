package org.jsp.reservationapi.service;

import java.util.Optional;

import org.jsp.reservationapi.dao.BusDao;
import org.jsp.reservationapi.dao.TicketDao;
import org.jsp.reservationapi.dao.UserDao;
import org.jsp.reservationapi.dto.TicketResponse;
import org.jsp.reservationapi.exception.BusNotFoundException;
import org.jsp.reservationapi.exception.UsernotFoundException;
import org.jsp.reservationapi.model.Bus;
import org.jsp.reservationapi.model.Ticket;
import org.jsp.reservationapi.model.User;
import org.jsp.reservationapi.util.AccountStatus;
import org.jsp.reservationapi.util.TicketStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

	@Autowired
	private TicketDao ticketDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private BusDao busDao;
	
	
	
	public  TicketResponse saveTicket(int userId,int busId, int numberOfSeats) {
		Optional<Bus> recBus=busDao.findByid(busId);
		Optional<User> recUser=userDao.findById(userId);
		
		System.out.println(recUser);
		if(recBus.isEmpty())
			throw new BusNotFoundException("Cannot Book Ticket as Bus Id is Invalid");
		if(recUser.isEmpty()) 
			 throw new UsernotFoundException("Cannot Book Ticket as User Id is Invalid");
		   User dbUser=recUser.get();
		if (dbUser.getStatus().equals(AccountStatus.IN_ACTIVE.toString()))
			throw new IllegalStateException("Please Activate Your Account, Then book tickets");
		Bus dbBus = recBus.get();
		if (dbBus.getAvalable_seat() < numberOfSeats)
			throw new IllegalArgumentException("Insufficient seats");
		Ticket ticket = new Ticket();
		ticket.setCost(numberOfSeats * dbBus.getCost_per_seat());
		ticket.setStatus(TicketStatus.BOOKED.toString());
		ticket.setBus(dbBus);// Assigning Bus to ticket
		ticket.setUser(dbUser);// Assigning User to ticket
		ticket.setNumberOfSeatesBooked(numberOfSeats);
		dbBus.getBookedTickets().add(ticket);// Adding ticket to bus
		dbUser.getTickets().add(ticket);// Adding Ticket to User
		dbBus.setAvalable_seat(dbBus.getAvalable_seat() - numberOfSeats);
		userDao.saveUser(dbUser);                   // Updating User
		busDao.saveBus(dbBus);                    // Updating Bus
		ticket = ticketDao.saveTicket(ticket);
		
		 return mapToTicketResponse(ticket, dbBus, dbUser);
			 
		 
		
	}
	
	
	public TicketResponse mapToTicketResponse(Ticket ticket, Bus bus, User user) {
		TicketResponse ticketResponse = new TicketResponse();
		ticketResponse.setAge(user.getAge());
		ticketResponse.setBusName(bus.getName());
		ticketResponse.setBusNumber(bus.getBus_no());
		ticketResponse.setCost(ticket.getCost());
		ticketResponse.setDateAndTimeOfBooking(ticket.getDateAndTimeOfBooking());
		ticketResponse.setDateOfDeparture(bus.getDepature_date());
		ticketResponse.setFormLocation(bus.getForm_loc());
		ticketResponse.setGender(user.getGender());
		ticketResponse.setId(ticket.getId());
		ticketResponse.setNumberOfSeatesBooked(ticket.getNumberOfSeatesBooked());
		ticketResponse.setPhone(user.getPhone());
		ticketResponse.setToLocation(bus.getTo_loc());
		ticketResponse.setStatus(ticket.getStatus());
		ticketResponse.setUserName(user.getName());
		return ticketResponse;

	}
	
	
}
