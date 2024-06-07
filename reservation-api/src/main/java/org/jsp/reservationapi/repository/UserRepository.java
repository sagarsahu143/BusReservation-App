package org.jsp.reservationapi.repository;

import java.util.Optional;

import org.jsp.reservationapi.model.Admin;
import org.jsp.reservationapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByIdAndPassword(int id, String password);

	Optional<User> findByEmailAndPassword(String email, String password);

	Optional<User> findByToken(String token);

	Optional<User> findByEmail(String email);

}
