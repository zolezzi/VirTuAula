package ar.edu.unq.virtuaula.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ar.edu.unq.virtuaula.model.Account;
import ar.edu.unq.virtuaula.model.StudentAccount;

public interface AccountRepository extends JpaRepository<Account, Long> {

	public Optional<Account> findByDni(Integer dni);
	
	@Query("SELECT sa FROM StudentAccount sa WHERE sa.id in (:studentIds)")
	public List<StudentAccount> findAllStudentByIds(@Param("studentIds") List<Long> studentIds);

}
