package ar.edu.unq.virtuaula.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.edu.unq.virtuaula.constants.LevelConstants;
import ar.edu.unq.virtuaula.model.Level;
import ar.edu.unq.virtuaula.repository.LevelRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LevelService {

    private final LevelRepository levelRepository;
    
    public Level getInitialLevel() {
    	return levelRepository.findByName(LevelConstants.BEGINNER_NAME);
    }
    
    public Level getNextLevel(Level level) {
    	return levelRepository.findByName(level.getNameNextLevel());
    }
}
