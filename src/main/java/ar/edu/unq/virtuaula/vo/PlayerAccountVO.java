package ar.edu.unq.virtuaula.vo;

import ar.edu.unq.virtuaula.dto.LevelDTO;
import lombok.Data;

@Data
public class PlayerAccountVO {

    private Long id;
    private String username;
    private String firstName;
    private LevelDTO level;
    private Double experience;
    private int progress;
}
