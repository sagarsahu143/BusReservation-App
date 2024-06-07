 package org.jsp.reservationapi.service;

import java.sql.Struct;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.jsp.reservationapi.dao.AdminDao;
import org.jsp.reservationapi.dao.BusDao;
import org.jsp.reservationapi.dto.BusRequest;
import org.jsp.reservationapi.dto.ResponseStructor;
import org.jsp.reservationapi.exception.BusNotFoundException;
import org.jsp.reservationapi.model.Admin;
import org.jsp.reservationapi.model.Bus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import net.bytebuddy.implementation.bytecode.Throw;

@Service
public class BusService {

	@Autowired
	private BusDao busDao;
	@Autowired
	private AdminDao adminDao;

	public ResponseEntity<ResponseStructor<Bus>> saveBus(BusRequest busRequest, int admin_id) {
		Optional<Admin> recAdmin = adminDao.findById(admin_id);
		ResponseStructor<Bus> structor =new ResponseStructor<>();	
		if (recAdmin.isPresent()) {
			Bus bus=MaptoBus(busRequest);
			
			bus.setAvalable_seat(bus.getTotal_seat());
			bus.setAdmins(recAdmin.get());
			recAdmin.get().getBuss().add(bus);
			adminDao.saveAdmin(recAdmin.get());
			
			busDao.saveBus(bus);
			structor.setData(bus);
			structor.setMessage("bus added");
			structor.setStatusCode(HttpStatus.CREATED.value());
			return ResponseEntity.status(HttpStatus.CREATED).body(structor);
		}
		return null;
	}
	
	public ResponseEntity<ResponseStructor<Bus>> updateBus( int id, BusRequest busRequest) {
		Optional<Bus> recbus=busDao.findByid(id);
		ResponseStructor<Bus> structor=new ResponseStructor<>();
		if(recbus.isPresent()) {
			Bus dbBus=recbus.get();
			
			dbBus.setName(busRequest.getName());
			dbBus.setBus_no(busRequest.getBus_no());
			dbBus.setDepature_date(busRequest.getDepature_date());
			dbBus.setForm_loc(busRequest.getForm_loc());
			dbBus.setTo_loc(busRequest.getTo_loc());
			dbBus.setTotal_seat(busRequest.getTotal_seat());
			dbBus.setCost_per_seat(busRequest.getCost());
			dbBus.setAvalable_seat(busRequest.getTotal_seat());
			dbBus=busDao.saveBus(dbBus);
			structor.setData(dbBus);
			structor.setMessage("Bus update succesfully");
			structor.setStatusCode(HttpStatus.ACCEPTED.value());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(structor);
			
		}
		
		return null;
	}
	
	
	public ResponseEntity<ResponseStructor<Bus>> findById(int id) {
		ResponseStructor<Bus> structor=new ResponseStructor<>();
		Optional<Bus>recBus=busDao.findByid(id);
		if(recBus.isPresent()) {
			structor.setMessage("bus  found");
			structor.setData(recBus.get());
			structor.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structor);
		}
		return null;
	}
	
	
	
	
	public ResponseEntity<ResponseStructor<Bus>> findByBusNumber(String bus_no) {
		ResponseStructor<Bus> structor =new ResponseStructor<>();
		Optional<Bus> recBus=busDao.findByBusNumber(bus_no);
		if(recBus.isPresent()) {
			structor.setMessage("bus found");
			structor.setData(recBus.get());
			structor.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structor);
		}
		
		return null;
	}
//	
	public ResponseEntity<ResponseStructor<String>> delete(int id) {
		ResponseStructor<String> structor =new ResponseStructor<>();	
		Optional<Bus> recBus= busDao.findByid(id);
		if(recBus.isPresent()) {
			busDao.delete(id);
			structor.setData("bus delete");
			structor.setMessage("bus found");
			structor.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structor);
		}
		return null;
	}

	
	public ResponseEntity<ResponseStructor<List<Bus>>> findAll() {
		ResponseStructor<List<Bus>> structor =new ResponseStructor<>();
		structor.setData(busDao.findAll());
		structor.setMessage("busses founde");
		structor.setStatusCode(HttpStatus.OK.value());
		return ResponseEntity.status(HttpStatus.OK).body(structor);
		
	}
	
	
	
	
	public ResponseEntity<ResponseStructor< List<Bus>>> findBuses(String form_loc,String to_loc ,LocalDate depature_date){
		
		ResponseStructor<List<Bus>> structor=new ResponseStructor<>();
		
		List<Bus> buslist=busDao.findBuses(form_loc, to_loc,depature_date);
		
		if(buslist.isEmpty()) 
			
			throw new BusNotFoundException("wbdjhg");
			
			structor.setData(buslist);
			structor.setMessage("Bus found");
			structor.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structor);
			
		
		
		
	}
	
	public Bus MaptoBus(BusRequest busRequest) {
	return	Bus.builder().name(busRequest.getName()).bus_no(busRequest.getBus_no()).form_loc(busRequest.getForm_loc()).to_loc(busRequest.getTo_loc())
		.depature_date(busRequest.getDepature_date()).total_seat(busRequest.getTotal_seat()).cost_per_seat(busRequest.getCost()).build(); 
	}
}
