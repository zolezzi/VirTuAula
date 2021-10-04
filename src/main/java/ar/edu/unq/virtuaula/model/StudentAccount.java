package ar.edu.unq.virtuaula.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

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
    
    private Double experience;
}
