package ar.edu.unq.virtuaula.repository;

import ar.edu.unq.virtuaula.model.Classroom;
import ar.edu.unq.virtuaula.model.Lesson;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestLessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findByClassroom(Classroom classroom);
}
