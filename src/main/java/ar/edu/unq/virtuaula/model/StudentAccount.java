package ar.edu.unq.virtuaula.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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
    
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "level_id", referencedColumnName = "id")
    private Level level;
    
    private Double experience;
    
    private Integer life;

	public Double calculateExperience(Double experienceActual, Double experienceCalculated) {
		return experienceActual == null || experienceCalculated == null 
				? this.experience : experienceActual + experienceCalculated;
	}

	public void addTeacher(TeacherAccount teacherAccount) {
		if(!this.getTeachers().contains(teacherAccount)) {
			this.getTeachers().add(teacherAccount);
		}
	}

	public Integer lifeSum(Integer life, Integer calculatedValue) {
		return life + calculatedValue;
	}
}
