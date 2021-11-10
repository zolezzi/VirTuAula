package ar.edu.unq.virtuaula.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.edu.unq.virtuaula.model.Buffer;
import ar.edu.unq.virtuaula.model.Level;
import ar.edu.unq.virtuaula.model.StudentAccount;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ManagementBufferService {

	
	public void applyBufferInStudentAccount(Level level, StudentAccount student, Double note) {
		applyBufferExprience(level.getBuffers().stream().filter( buffer -> buffer.isExperience()).collect(Collectors.toList()), student, note);
		applyBufferLife(level.getBuffers().stream().filter( buffer -> buffer.isLife()).collect(Collectors.toList()), student);
	}

	private void applyBufferLife(List<Buffer> buffers, StudentAccount student) {
		final int lifeCalculated = 0;
		buffers.stream().forEach(buffer -> buffer.apply(student, Double.valueOf(lifeCalculated)));
	}

	private void applyBufferExprience(List<Buffer> buffers, StudentAccount student, Double note) {
		buffers.stream().forEach(buffer -> buffer.apply(student, note));
	}
}
