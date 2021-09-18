package ar.edu.unq.virtuaula.service;

import ar.edu.unq.virtuaula.dto.LessonDTO;
import ar.edu.unq.virtuaula.model.Classroom;
import ar.edu.unq.virtuaula.model.Lesson;
import ar.edu.unq.virtuaula.repository.GuestLessonRepository;
import ar.edu.unq.virtuaula.util.MapperUtil;
import java.util.Arrays;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class GuestLessonService {
    
    private final GuestLessonRepository guestLessonRepository;
    private final MapperUtil mapperUtil;

    public List<LessonDTO> getAllByClassroom(Classroom classroom) {
        List<Lesson> lessons = guestLessonRepository.findByClassroom(classroom);
        return Arrays.asList(mapperUtil.getMapper().map(lessons, LessonDTO[].class));
    }

}
