package ar.edu.unq.virtuaula.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class TaskDTO implements Serializable{

	private static final long serialVersionUID = -7590328703265446470L;
	private Long id;
    private String statement;
    private List<OptionTaskDTO> options;
    private Long answer;
}
