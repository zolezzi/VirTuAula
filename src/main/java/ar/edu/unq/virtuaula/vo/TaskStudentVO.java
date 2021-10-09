package ar.edu.unq.virtuaula.vo;

import java.util.List;

import lombok.Data;

@Data
public class TaskStudentVO {

    private Long id;
    private String statement;
    private List<OptionTaskVO> options;
    private Double score;
    private Long answer;
}
