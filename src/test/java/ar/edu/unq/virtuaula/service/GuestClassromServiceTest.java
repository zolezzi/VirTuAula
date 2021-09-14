package ar.edu.unq.virtuaula.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;
import ar.edu.unq.virtuaula.dto.ClassroomDTO;
import ar.edu.unq.virtuaula.model.Classroom;
import ar.edu.unq.virtuaula.repository.GuestClassromRepository;

public class GuestClassromServiceTest extends VirtuaulaApplicationTests {

	@Mock
	private GuestClassromRepository repository;
	
	@Autowired
	private GuestClassromService guestClassromService;
	
	@Test
	public void getAllwithClassroomReturnNotEmptyList() {
		int expected = 1;
		Classroom classroom = new Classroom("Matematicas");
		List<Classroom> listClassrooms = new ArrayList<>();
		listClassrooms.add(classroom);
		when(repository.findAll()).thenReturn(listClassrooms);
		List<ClassroomDTO> result = guestClassromService.getAll();
		assertEquals(expected, result.size());
	}
}
