package org.jsp.reservationapi.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.jsp.reservationapi.model.Bus;
import org.jsp.reservationapi.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BusDao {
@Autowired
	private BusRepository busRepository;
	
	public Bus saveBus(Bus bus) {
		return busRepository.save(bus);
	}
	
	
	
	public Bus updateBus(int id,Bus bus) {
		return busRepository.save(bus);
	}
	
	public Optional<Bus> findByid(int id) {
		return busRepository.findById(id);
		
		
		
	}
	public Optional<Bus> findByBusNumber( String bus_no){
		return busRepository.findByBusNumber(bus_no);
	}
	
	public List<Bus> findAll() {
		return busRepository.findAll();
	}
	
	public List<Bus> findBuses(String form_loc,String to_loc, LocalDate depature_date) {
		return busRepository.findBuses(form_loc, to_loc,depature_date);
	}
	
	public void delete(int id) {
		busRepository.deleteById(id);
	}
	
	
	
}
