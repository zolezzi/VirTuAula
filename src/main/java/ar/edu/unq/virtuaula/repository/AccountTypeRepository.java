package ar.edu.unq.virtuaula.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unq.virtuaula.model.AccountType;

public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {

    public AccountType findByName(String name);

}
