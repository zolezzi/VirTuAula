package ar.edu.unq.virtuaula.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unq.virtuaula.model.TaskType;

public interface TaskTypeRepository extends JpaRepository<TaskType, Long>{

}
