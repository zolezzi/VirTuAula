package ar.edu.unq.virtuaula.vo;

import java.util.List;

import ar.edu.unq.virtuaula.dto.NewGameDTO;
import lombok.Data;

@Data
public class TeamVO {

	private Long id;
	private String name;
	private NewGameDTO newGame;
	private List<PlayerAccountVO> playerAccounts;
}
