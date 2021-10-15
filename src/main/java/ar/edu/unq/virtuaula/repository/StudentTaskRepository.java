package ar.edu.unq.virtuaula.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ar.edu.unq.virtuaula.model.Lesson;
import ar.edu.unq.virtuaula.model.StudentTask;

public interface StudentTaskRepository extends JpaRepository<StudentTask, Long>{

	public List<StudentTask> findByLesson(Lesson lesson);

	@Query("SELECT st FROM StudentTask st WHERE st.task.id = ?1 and st.studentAccount.id = ?2")
	public Optional<StudentTask> findByTaskIdAndStudentId(Long id, Long studentId);

	@Query("SELECT st FROM StudentTask st WHERE st.lesson.id = ?1 and st.studentAccount.id = ?2")
	public List<StudentTask> findByLessonAndStudent(Long lessonId, Long studentId);
}
