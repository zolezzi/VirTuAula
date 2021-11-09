package ar.edu.unq.virtuaula.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class TeamDTO implements Serializable{

	private static final long serialVersionUID = 1113313701062314510L;
	private ClassroomDTO classroom;
	private AccountDTO teacher;
	private List<AccountDTO> studentAccounts;
}
