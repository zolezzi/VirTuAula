package ar.edu.unq.virtuaula.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unq.virtuaula.model.TeacherAccount;
import ar.edu.unq.virtuaula.model.Team;

public interface TeamRepository extends JpaRepository<Team, Long>{

	public List<Team> findByTeacher(TeacherAccount teacherAccount);
}
