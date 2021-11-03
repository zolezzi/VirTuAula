package ar.edu.unq.virtuaula.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unq.virtuaula.model.Level;

public interface LevelRepository extends JpaRepository<Level, Long>{

	public Level findByName(String name);
}
