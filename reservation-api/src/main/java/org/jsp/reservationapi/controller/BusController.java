package org.jsp.reservationapi.controller;

import org.jsp.reservationapi.dto.ResponseStructor;
import org.jsp.reservationapi.model.Bus;
import org.jsp.reservationapi.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/buss")
public class BusController {
	
	@Autowired
	private BusService busService;

	@PostMapping("{admin_id}")
	public ResponseEntity<ResponseStructor<Bus>> saveBus(@RequestBody Bus bus,@PathVariable int admin_id) {
		return busService.saveBus(bus,admin_id);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<ResponseStructor<String>> deleBus(@PathVariable int id) {
		return busService.delete(id);
	}

}
