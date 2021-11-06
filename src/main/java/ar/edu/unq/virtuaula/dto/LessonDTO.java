package ar.edu.unq.virtuaula.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class LessonDTO implements Serializable {

    private static final long serialVersionUID = -225949991242303086L;
    private Long id;
    private String name;
    private Long classroomId;
    private int maxNote;
    private List<TaskDTO> tasks;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="America/Argentina/Buenos_Aires")
    private Date deliveryDate;

}
