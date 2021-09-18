package ar.edu.unq.virtuaula.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import ar.edu.unq.virtuaula.dto.ClassroomDTO;
import ar.edu.unq.virtuaula.model.Classroom;
import ar.edu.unq.virtuaula.repository.GuestClassromRepository;
import ar.edu.unq.virtuaula.util.MapperUtil;

@ExtendWith(MockitoExtension.class)
public class GuestClassromServiceTest {

	@Mock
	private GuestClassromRepository repository;
	
	@Mock
	private MapperUtil mapperUtil;
	
	@InjectMocks
	private GuestClassromService guestClassromService = new GuestClassromService();
	
	@Test
	public void getAllwithClassroomReturnNotEmptyList() {
		int expected = 1;
		ModelMapper mapper = Mockito.mock(ModelMapper.class);
		Classroom classroom = new Classroom("Matematicas");
		List<Classroom> listClassrooms = new ArrayList<>();
		ClassroomDTO [] listClassroomDTOs = new ClassroomDTO[1];
		ClassroomDTO dto = new ClassroomDTO();
		listClassroomDTOs[0] = dto;
		listClassrooms.add(classroom);
		Mockito.when(mapper.map(listClassrooms, ClassroomDTO[].class)).thenReturn(listClassroomDTOs);
		Mockito.when(mapperUtil.getMapper()).thenReturn(mapper);
		Mockito.when(repository.findAll()).thenReturn(listClassrooms);
		List<ClassroomDTO> result = guestClassromService.getAll();
		assertEquals(expected, result.size());
	}
}
