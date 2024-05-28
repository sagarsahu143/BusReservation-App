package org.jsp.reservationapi.service;

import java.util.Optional;

import org.jsp.reservationapi.dao.AdminDao;
import org.jsp.reservationapi.dao.BusDao;
import org.jsp.reservationapi.dto.ResponseStructor;
import org.jsp.reservationapi.model.Admin;
import org.jsp.reservationapi.model.Bus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BusService {

	@Autowired
	private BusDao busDao;
	@Autowired
	private AdminDao adminDao;

	public ResponseEntity<ResponseStructor<Bus>> saveBus(Bus bus, int admin_id) {
		Optional<Admin> recAdmin = adminDao.findById(admin_id);
		if (recAdmin.isPresent()) {
			Admin admin = recAdmin.get();
			bus.setAdmins(admin);
			admin.getBuss().add(bus);
			ResponseStructor<Bus> structor = new ResponseStructor<>();
			structor.setData(busDao.saveBus(bus));
			adminDao.saveAdmin(admin);
			structor.setMessage("bus added");
			structor.setStatusCode(HttpStatus.CREATED.value());
			return ResponseEntity.status(HttpStatus.CREATED).body(structor);
		}
		return null;
	}
	
	
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

}
