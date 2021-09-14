package ar.edu.unq.virtuaula.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.virtuaula.dto.ClassroomDTO;
import ar.edu.unq.virtuaula.service.GuestClassromService;

@RestController
@RequestMapping("/api")
public class GuestClassromRestController {

	@Autowired
	private GuestClassromService service;
	
	@GetMapping("/classrooms")
	public List<ClassroomDTO> getAll(){
		return service.getAll();
	}
}
