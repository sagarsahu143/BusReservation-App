package org.jsp.reservationapi.dao;

import java.util.List;
import java.util.Optional;

import org.jsp.reservationapi.dto.AdminRequest;
import org.jsp.reservationapi.model.Admin;
import org.jsp.reservationapi.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AdminDao {

	@Autowired
	private AdminRepository adminRepository;

	public Admin saveAdmin(Admin admin) {
		return adminRepository.save(admin);
	}
	
	public Admin update(Admin admin) {
		return adminRepository.save(admin);
	}

	public Optional<Admin> findById(int id) {
		return adminRepository.findById(id);

	}
	public Optional<Admin> verify(String email,String password) {
		return adminRepository.findByEmailAndPassword(email, password);
	}
	
	public List<Admin> findAll() {
		return adminRepository.findAll();
	}

	public void delete(int id) {
		adminRepository.deleteById(id);
	}
	
	public Optional<Admin> findByToken(String token) {
		return adminRepository.findByToken(token);
	}
	
	
	public Optional<Admin> findByEmail(String email) {
		return adminRepository.findByEmail(email);
	}
}
