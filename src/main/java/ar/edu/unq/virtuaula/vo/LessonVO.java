package ar.edu.unq.virtuaula.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class LessonVO {

    private Long id;
    private String name;
    private int progress;
    private Long classroomId;
    private Double note;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="America/Argentina/Buenos_Aires")
    private Date deliveryDate;
}
