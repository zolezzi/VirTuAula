package ar.edu.unq.virtuaula.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.edu.unq.virtuaula.model.Buffer;
import ar.edu.unq.virtuaula.model.Level;
import ar.edu.unq.virtuaula.model.PlayerAccount;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ManagementBufferService {

	
	public void applyBufferInPlayerAccount(Level level, PlayerAccount player, Double note) {
		applyBufferExprience(level.getBuffers().stream().filter( buffer -> buffer.isExperience()).collect(Collectors.toList()), player, note);
		applyBufferLife(level.getBuffers().stream().filter( buffer -> buffer.isLife()).collect(Collectors.toList()), player);
	}

	private void applyBufferLife(List<Buffer> buffers, PlayerAccount player) {
		final int lifeCalculated = 0;
		buffers.stream().forEach(buffer -> buffer.apply(player, Double.valueOf(lifeCalculated)));
	}

	private void applyBufferExprience(List<Buffer> buffers, PlayerAccount player, Double note) {
		buffers.stream().forEach(buffer -> buffer.apply(player, note));
	}
}
