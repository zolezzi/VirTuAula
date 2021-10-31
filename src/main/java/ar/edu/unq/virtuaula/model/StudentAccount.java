package ar.edu.unq.virtuaula.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@DiscriminatorValue(value="StudentAccount")
public class StudentAccount extends Account {
	
	private static final long serialVersionUID = -6667231579400890583L;

    @ManyToMany(mappedBy = "students")
    private List<TeacherAccount> teachers = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "student_task_id")
    @JsonIgnoreProperties("studentTask")
    private List<StudentTask> resultsTasks = new ArrayList<>();
    
    private Double experience;

	public Double calculateExperience(Double note, int multiplier) {
		return note == null ? this.experience : this.experience + (note * multiplier);
	}

	public void addTeacher(TeacherAccount teacherAccount) {
		if(!this.getTeachers().contains(teacherAccount)) {
			this.getTeachers().add(teacherAccount);
		}
	}
}
