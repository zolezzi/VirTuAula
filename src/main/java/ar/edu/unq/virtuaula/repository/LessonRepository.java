package ar.edu.unq.virtuaula.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unq.virtuaula.model.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

}
