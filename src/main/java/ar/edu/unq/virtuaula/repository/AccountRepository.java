package ar.edu.unq.virtuaula.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ar.edu.unq.virtuaula.model.Account;
import ar.edu.unq.virtuaula.model.PlayerAccount;

public interface AccountRepository extends JpaRepository<Account, Long> {

	public Optional<Account> findByDni(Integer dni);
	
	@Query("SELECT pa FROM PlayerAccount pa WHERE pa.id in (:playerIds)")
	public List<PlayerAccount> findAllPlayerByIds(@Param("playerIds") List<Long> playerIds);

}
