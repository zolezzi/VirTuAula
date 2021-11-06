package ar.edu.unq.virtuaula.vo;

import ar.edu.unq.virtuaula.dto.ClassroomDTO;
import java.util.List;
import lombok.Data;

@Data
public class ClassroomVO {
    
    private ClassroomDTO classroom;
    private List<Long> students;
}
