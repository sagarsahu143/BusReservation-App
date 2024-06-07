package org.jsp.reservationapi.controller;

import java.time.LocalDate;
import java.util.List;

import org.jsp.reservationapi.dto.BusRequest;
import org.jsp.reservationapi.dto.ResponseStructor;
import org.jsp.reservationapi.model.Bus;
import org.jsp.reservationapi.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin
@RestController
@RequestMapping("/api/buss")
public class BusController {
	
	@Autowired
	private BusService busService;

	@PostMapping("/{admin_id}")
	public ResponseEntity<ResponseStructor<Bus>> saveBus(@RequestBody BusRequest busRequest,@PathVariable int admin_id) {
		return busService.saveBus(busRequest,admin_id);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructor<String>> deleteBus(@PathVariable int id) {
		return busService.delete(id);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructor<Bus>> finfById(@PathVariable int id) {
		return busService.findById(id);
	}
	@GetMapping("/find-by-busnumbre")
	public ResponseEntity<ResponseStructor<Bus>> findByBusNumber(@RequestParam String bus_no) {
		return busService.findByBusNumber(bus_no);
	}
	
	@GetMapping
	public ResponseEntity<ResponseStructor<List<Bus>>> findAll() {
		return busService.findAll();
	}
	
	@GetMapping("/find")
	public ResponseEntity<ResponseStructor<List<Bus>>> findBuses(@RequestParam String form_loc,@RequestParam String to_loc ,@RequestParam LocalDate depature_date) {
		return busService.findBuses(form_loc, to_loc,depature_date);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ResponseStructor<Bus>> updateBus(@PathVariable(name = "id")Integer id ,@RequestBody BusRequest busRequest) {
	
		return busService.updateBus(id, busRequest);
	}
   
}
