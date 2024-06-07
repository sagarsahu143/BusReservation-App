package org.jsp.reservationapi.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.jsp.reservationapi.model.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BusRepository extends JpaRepository<Bus, Integer> {
	
	
   @Query("select b from  Bus b where b.bus_no=?1")
	Optional<Bus> findByBusNumber(String bus_no);
   
   
   @Query("select b from Bus b where b.form_loc=?1 and b.to_loc=?2 and b.depature_date=?3")
     List<Bus> findBuses(String form_loc,String to_loc , LocalDate depature_date);
		
	
}
