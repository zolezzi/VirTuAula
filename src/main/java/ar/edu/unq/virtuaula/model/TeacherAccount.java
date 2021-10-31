package ar.edu.unq.virtuaula.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@DiscriminatorValue(value = "TeacherAccount")
public class TeacherAccount extends Account {

    private static final long serialVersionUID = -3455382936475619272L;

    @ManyToMany
    @JoinTable(
            name = "teachers_students",
            joinColumns = @JoinColumn(
                    name = "teacher_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "student_id", referencedColumnName = "id"))
    private List<StudentAccount> students = new ArrayList<>();

    public boolean containsClassroom(Classroom classroom) {
        return this.getClassrooms().isEmpty() ? false : this.getClassrooms()
                .stream()
                .map(classroomI -> classroomI.getId())
                .collect(Collectors.toList()).contains(classroom.getId());
    }

    public boolean containsLesson(Lesson lesson) {
        return this.getClassrooms().isEmpty() ? false : this.getClassrooms()
                .stream()
                .map(classroomI -> classroomI.getLessons())
                .flatMap(lessonI -> lessonI.stream())
                .collect(Collectors.toList()).contains(lesson);
    }

	public List<StudentAccount> getStudentsByDNIs(List<Integer> dnis) {
		return this.getStudents().stream().filter(student -> existsStudent(student,dnis)).collect(Collectors.toList());
	}

	private boolean existsStudent(StudentAccount student, List<Integer> dnis) {
		return dnis.contains(student.getDni());
	}
}
