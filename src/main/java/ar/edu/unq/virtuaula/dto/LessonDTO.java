package ar.edu.unq.virtuaula.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class LessonDTO implements Serializable {

    private static final long serialVersionUID = -225949991242303086L;
    private Long id;
    private String name;

    public LessonDTO() {
    }

    public LessonDTO(Long id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

}
