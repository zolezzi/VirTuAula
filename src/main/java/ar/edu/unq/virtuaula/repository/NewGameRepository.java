package ar.edu.unq.virtuaula.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unq.virtuaula.model.NewGame;

public interface NewGameRepository extends JpaRepository<NewGame, Long> {

}
