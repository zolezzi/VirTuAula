package ar.edu.unq.virtuaula.dto;

import java.util.List;

import lombok.Data;

@Data
public class TaskDTO {

    private Long id;
    private String statement;
    private List<OptionTaskDTO> options;
    private Long aswerSelectedId;
}
