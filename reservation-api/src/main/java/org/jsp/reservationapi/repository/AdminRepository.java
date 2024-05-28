package org.jsp.reservationapi.repository;

import org.jsp.reservationapi.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

}
