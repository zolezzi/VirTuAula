package ar.edu.unq.virtuaula.vo;

import java.util.List;

import ar.edu.unq.virtuaula.dto.ClassroomDTO;
import lombok.Data;

@Data
public class TeamVO {

	private Long id;
	private String name;
	private ClassroomDTO classroom;
	private List<StudentAccountVO> studentAccounts;
}
