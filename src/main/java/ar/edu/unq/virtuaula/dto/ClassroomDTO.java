package ar.edu.unq.virtuaula.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class ClassroomDTO implements Serializable {

    private static final long serialVersionUID = -225949991242303086L;
    private Long id;
    private String name;

    public ClassroomDTO() {
    }

    public ClassroomDTO(Long id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

}
