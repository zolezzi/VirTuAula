package ar.edu.unq.virtuaula.builder;

import java.util.List;

import ar.edu.unq.virtuaula.model.Classroom;
import ar.edu.unq.virtuaula.model.StudentAccount;
import ar.edu.unq.virtuaula.model.TeacherAccount;
import ar.edu.unq.virtuaula.model.Team;

public class TeamBuilder {

	private final Team instance = new Team();
	
	public static TeamBuilder teamWithName(String name) {
		TeamBuilder teamBuilder = new TeamBuilder();
		teamBuilder.instance.setName(name);
		return teamBuilder;
	}
	
    public TeamBuilder withClassroom(Classroom classroom) {
        this.instance.setClassroom(classroom);
        return this;
    }
	
    public TeamBuilder withStudents(List<StudentAccount> students) {
        this.instance.setStudents(students);
        return this;
    }
    
    public TeamBuilder withTeacher(TeacherAccount teacher) {
        this.instance.setTeacher(teacher);
        return this;
    }
    
    public Team build() {
        return this.instance;
    }
}
