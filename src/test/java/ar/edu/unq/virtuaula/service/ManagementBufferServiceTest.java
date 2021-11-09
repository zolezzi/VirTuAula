package ar.edu.unq.virtuaula.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;
import ar.edu.unq.virtuaula.model.Level;
import ar.edu.unq.virtuaula.model.StudentAccount;

public class ManagementBufferServiceTest extends VirtuaulaApplicationTests {

	@Autowired
	private ManagementBufferService managementBufferService;
	
	@Test
	public void whenStudentApplyBuffersOfALevelThenReturnStudentWithIncrementExperienceAndLife() {
		Double expected  = 20d;
		Integer expectedLife  = 4;
		Level level = createLevelWithTwoBuffer();
		StudentAccount student = (StudentAccount) createOneStudentAccount();
		managementBufferService.ApplyBufferInStudentAccount(level, student, 10d);
		assertEquals(expected , student.getExperience());
		assertEquals(expectedLife , student.getLife());
	}
}
