package ar.edu.unq.virtuaula.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class NewGameDTO implements Serializable {

    private static final long serialVersionUID = -225949991242303086L;
    private Long id;
    private String name;
    private String description;
    private int progress;

}
