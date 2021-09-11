package ar.edu.unq.virtuaula.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unq.virtuaula.model.Classroom;

public interface GuestClassromRepository extends JpaRepository<Classroom, Long> {

}
