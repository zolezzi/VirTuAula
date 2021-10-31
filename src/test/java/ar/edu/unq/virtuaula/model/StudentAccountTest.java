package ar.edu.unq.virtuaula.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;

public class StudentAccountTest extends VirtuaulaApplicationTests{

    @Test
    public void testAddTeacherForStudent() {
    	StudentAccount student = (StudentAccount) createOneStudentAccount();
    	TeacherAccount teacher = (TeacherAccount) createOneTeacherAccount();
    	student.addTeacher(teacher);
    	assertNotNull(student.getTeachers());
    }
	
}
