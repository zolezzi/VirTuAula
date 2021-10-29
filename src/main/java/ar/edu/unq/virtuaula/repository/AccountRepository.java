package ar.edu.unq.virtuaula.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unq.virtuaula.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

	public Optional<Account> findByDni(Integer dni);

}
