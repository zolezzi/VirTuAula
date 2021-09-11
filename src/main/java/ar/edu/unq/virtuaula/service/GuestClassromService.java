package ar.edu.unq.virtuaula.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unq.virtuaula.model.Classroom;
import ar.edu.unq.virtuaula.repository.GuestClassromRepository;

@Service
public class GuestClassromService {

	@Autowired
	private GuestClassromRepository repository;
	
	public List<Classroom> getAll() {
		return repository.findAll();
	}
}
