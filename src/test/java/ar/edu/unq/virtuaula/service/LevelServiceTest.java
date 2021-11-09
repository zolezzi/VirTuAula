package ar.edu.unq.virtuaula.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;
import ar.edu.unq.virtuaula.model.Level;

public class LevelServiceTest extends VirtuaulaApplicationTests {

	@Autowired
	private LevelService levelService;
	
	@Test
	public void testGetLevelInitialThenReturnLevelInitial() {
		String expected = "Beginner";
		createLevelBeginner();
		Level result = levelService.getInitialLevel();
		assertEquals(expected , result.getName());
	}
	
	@Test
	public void getNextLevelThenReturnLevelCorrespondent() {
		String expected = "Profesional";
		Level level = createLevelBeginner();
		createLevelProfesional();
		Level result = levelService.getNextLevel(level);
		assertNotNull(result);
		assertEquals(expected , result.getName());
	}
}
